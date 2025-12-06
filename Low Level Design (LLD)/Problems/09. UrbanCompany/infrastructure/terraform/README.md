# Terraform Infrastructure

This directory contains Terraform configurations for provisioning and managing the Urban Company infrastructure on AWS.

## Directory Structure

```
terraform/
├── main.tf                 # Main infrastructure configuration
├── variables.tf           # Variable definitions
├── outputs.tf            # Output definitions
├── modules/              # Reusable Terraform modules
│   ├── vpc/             # VPC networking module
│   ├── eks/             # EKS cluster module
│   └── rds/             # RDS database module
└── environments/         # Environment-specific configurations
    ├── dev/             # Development environment
    ├── staging/         # Staging environment
    └── prod/            # Production environment
```

## Prerequisites

- [Terraform](https://www.terraform.io/downloads.html) >= 1.6.0
- [AWS CLI](https://aws.amazon.com/cli/) configured with appropriate credentials
- Valid AWS account with necessary permissions

## Modules

### VPC Module

Creates a complete VPC with public and private subnets across multiple availability zones.

**Features:**
- Public and private subnets across 3 AZs
- NAT Gateways (configurable: single or multi-AZ)
- Internet Gateway
- Route tables with proper routing
- VPC Flow Logs to CloudWatch
- Kubernetes-specific subnet tags

**Usage:**
```hcl
module "vpc" {
  source = "./modules/vpc"
  
  environment          = "prod"
  vpc_cidr            = "10.0.0.0/16"
  availability_zones  = ["us-east-1a", "us-east-1b", "us-east-1c"]
  enable_nat_gateway  = true
  single_nat_gateway  = false
}
```

### EKS Module

Provisions an EKS cluster with managed node groups and necessary IAM roles.

**Features:**
- EKS cluster with configurable Kubernetes version
- CloudWatch logging for API server, audit, etc.
- OIDC provider for IRSA (IAM Roles for Service Accounts)
- Multiple managed node groups with auto-scaling
- IAM roles for cluster autoscaler and AWS Load Balancer Controller
- Support for cluster addons (VPC CNI, CoreDNS, kube-proxy)

**Usage:**
```hcl
module "eks" {
  source = "./modules/eks"
  
  cluster_name    = "urban-company-prod"
  cluster_version = "1.28"
  vpc_id          = module.vpc.vpc_id
  subnet_ids      = module.vpc.private_subnet_ids
  
  node_groups = {
    general = {
      desired_size = 3
      min_size     = 2
      max_size     = 10
      instance_types = ["t3.large"]
      capacity_type  = "ON_DEMAND"
    }
  }
}
```

### RDS Module

Creates PostgreSQL RDS instances with best practices.

**Features:**
- PostgreSQL database instances
- Encryption at rest using KMS
- Multi-AZ deployment support
- Automated backups with configurable retention
- Enhanced monitoring and Performance Insights
- Security group with restricted access
- Password stored in SSM Parameter Store
- CloudWatch log exports

**Usage:**
```hcl
module "user_db" {
  source = "./modules/rds"
  
  identifier          = "user-service-db"
  database_name       = "user_service_db"
  username            = "user_service"
  engine_version      = "15.4"
  instance_class      = "db.r6g.large"
  allocated_storage   = 100
  multi_az           = true
  vpc_id             = module.vpc.vpc_id
  subnet_ids         = module.vpc.private_subnet_ids
  environment        = "prod"
}
```

## Environment Setup

### Development Environment

Cost-optimized configuration for development:
- SPOT instances for worker nodes
- Single NAT gateway
- Smaller database instances (db.t3.small)
- Minimal Redis and Kafka configuration
- Shorter backup retention (7 days)

### Staging Environment

Balanced configuration for testing:
- Mix of ON_DEMAND and SPOT instances
- Multi-AZ NAT gateways
- Medium database instances (db.t3.medium)
- 14-day backup retention
- Replica mode for Redis

### Production Environment

High-availability configuration:
- ON_DEMAND instances only
- Multi-AZ everything
- Large database instances (db.r6g.xlarge)
- 3 Kafka brokers
- 30-day backup retention
- Full monitoring and logging

## Deployment

### Initialize Terraform

```bash
cd infrastructure/terraform/environments/prod
terraform init
```

### Plan Changes

```bash
terraform plan -out=tfplan
```

### Apply Infrastructure

```bash
terraform apply tfplan
```

### Destroy Infrastructure

```bash
terraform destroy
```

## State Management

**Important:** Use remote state backend for production environments.

Configure S3 backend in your environment's `main.tf`:

```hcl
terraform {
  backend "s3" {
    bucket         = "urban-company-terraform-state"
    key            = "prod/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-state-lock"
  }
}
```

## Outputs

After deployment, Terraform will output important connection details:

- VPC ID and subnet IDs
- EKS cluster endpoint and certificate
- RDS connection endpoints
- Redis endpoint
- Kafka broker endpoints
- S3 bucket name
- CloudFront distribution domain

Example:
```bash
terraform output -json > outputs.json
```

## Security Best Practices

1. **Secrets Management**
   - Store database passwords in AWS Secrets Manager or SSM Parameter Store
   - Never commit secrets to version control
   - Use IAM roles instead of access keys where possible

2. **Network Security**
   - All databases in private subnets
   - Security groups with minimum required access
   - VPC Flow Logs enabled for audit

3. **Encryption**
   - RDS encryption at rest enabled
   - EKS secrets encryption using KMS
   - S3 bucket encryption enabled

4. **IAM**
   - Use IRSA for pod-level permissions
   - Follow principle of least privilege
   - Enable CloudTrail for audit logging

## Cost Optimization

1. **Development**
   - Use SPOT instances for non-critical workloads
   - Single NAT gateway
   - Smaller instance types

2. **Scaling**
   - Enable cluster autoscaler
   - Use HPA for pod scaling
   - Set appropriate resource requests/limits

3. **Storage**
   - Configure appropriate backup retention
   - Use lifecycle policies for S3
   - Monitor and optimize database storage

## Maintenance

### Upgrading EKS

```bash
# Update cluster_version in terraform.tfvars
terraform plan
terraform apply
```

### Scaling Resources

Update the respective variable in `terraform.tfvars`:
```hcl
eks_node_groups = {
  general = {
    desired_size = 5  # Changed from 3
    min_size     = 3
    max_size     = 15
  }
}
```

Then apply:
```bash
terraform apply
```

## Troubleshooting

### Common Issues

1. **Insufficient Capacity**
   - Use multiple instance types in node groups
   - Spread across multiple AZs

2. **State Lock Issues**
   ```bash
   terraform force-unlock <lock-id>
   ```

3. **Resource Timeouts**
   - Increase timeout values in resource definitions
   - Check AWS service limits

## Additional Resources

- [AWS EKS Best Practices](https://aws.github.io/aws-eks-best-practices/)
- [Terraform AWS Provider Documentation](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)
- [VPC Design Best Practices](https://docs.aws.amazon.com/vpc/latest/userguide/vpc-network-design.html)
