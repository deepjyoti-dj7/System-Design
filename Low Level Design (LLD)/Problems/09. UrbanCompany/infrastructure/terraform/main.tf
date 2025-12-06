terraform {
  required_version = ">= 1.6.0"
  
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.23"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "~> 2.11"
    }
  }

  backend "s3" {
    bucket         = "urban-company-terraform-state"
    key            = "infrastructure/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-state-lock"
  }
}

provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      Project     = "UrbanCompany"
      Environment = var.environment
      ManagedBy   = "Terraform"
    }
  }
}

# Data source for availability zones
data "aws_availability_zones" "available" {
  state = "available"
}

# VPC Module
module "vpc" {
  source = "./modules/vpc"

  project_name        = var.project_name
  environment         = var.environment
  vpc_cidr            = var.vpc_cidr
  availability_zones  = data.aws_availability_zones.available.names
  private_subnet_cidrs = var.private_subnet_cidrs
  public_subnet_cidrs  = var.public_subnet_cidrs
  enable_nat_gateway   = var.enable_nat_gateway
  single_nat_gateway   = var.single_nat_gateway
}

# EKS Cluster Module
module "eks" {
  source = "./modules/eks"

  project_name       = var.project_name
  environment        = var.environment
  cluster_version    = var.eks_cluster_version
  vpc_id             = module.vpc.vpc_id
  private_subnet_ids = module.vpc.private_subnet_ids
  
  node_groups = var.eks_node_groups
  
  enable_cluster_autoscaler = true
  enable_metrics_server     = true
  enable_aws_load_balancer_controller = true
}

# RDS PostgreSQL Databases Module
module "rds_user_service" {
  source = "./modules/rds"

  project_name          = var.project_name
  environment           = var.environment
  service_name          = "user-service"
  
  engine                = "postgres"
  engine_version        = "15.4"
  instance_class        = var.rds_instance_class
  allocated_storage     = var.rds_allocated_storage
  
  database_name         = "user_service_db"
  master_username       = "admin"
  
  vpc_id                = module.vpc.vpc_id
  subnet_ids            = module.vpc.private_subnet_ids
  vpc_cidr_blocks       = [var.vpc_cidr]
  
  backup_retention_period = var.rds_backup_retention
  multi_az               = var.rds_multi_az
  deletion_protection    = var.rds_deletion_protection
}

module "rds_booking_service" {
  source = "./modules/rds"

  project_name          = var.project_name
  environment           = var.environment
  service_name          = "booking-service"
  
  engine                = "postgres"
  engine_version        = "15.4"
  instance_class        = var.rds_instance_class
  allocated_storage     = var.rds_allocated_storage
  
  database_name         = "booking_service_db"
  master_username       = "admin"
  
  vpc_id                = module.vpc.vpc_id
  subnet_ids            = module.vpc.private_subnet_ids
  vpc_cidr_blocks       = [var.vpc_cidr]
  
  backup_retention_period = var.rds_backup_retention
  multi_az               = var.rds_multi_az
  deletion_protection    = var.rds_deletion_protection
}

module "rds_payment_service" {
  source = "./modules/rds"

  project_name          = var.project_name
  environment           = var.environment
  service_name          = "payment-service"
  
  engine                = "postgres"
  engine_version        = "15.4"
  instance_class        = var.rds_instance_class
  allocated_storage     = var.rds_allocated_storage
  
  database_name         = "payment_service_db"
  master_username       = "admin"
  
  vpc_id                = module.vpc.vpc_id
  subnet_ids            = module.vpc.private_subnet_ids
  vpc_cidr_blocks       = [var.vpc_cidr]
  
  backup_retention_period = var.rds_backup_retention
  multi_az               = var.rds_multi_az
  deletion_protection    = var.rds_deletion_protection
}

module "rds_partner_service" {
  source = "./modules/rds"

  project_name          = var.project_name
  environment           = var.environment
  service_name          = "partner-service"
  
  engine                = "postgres"
  engine_version        = "15.4"
  instance_class        = var.rds_instance_class
  allocated_storage     = var.rds_allocated_storage
  
  database_name         = "partner_service_db"
  master_username       = "admin"
  
  vpc_id                = module.vpc.vpc_id
  subnet_ids            = module.vpc.private_subnet_ids
  vpc_cidr_blocks       = [var.vpc_cidr]
  
  backup_retention_period = var.rds_backup_retention
  multi_az               = var.rds_multi_az
  deletion_protection    = var.rds_deletion_protection
  
  # Enable PostGIS for geospatial queries
  parameter_group_family = "postgres15"
  parameters = [
    {
      name  = "shared_preload_libraries"
      value = "postgis"
    }
  ]
}

module "rds_catalog_service" {
  source = "./modules/rds"

  project_name          = var.project_name
  environment           = var.environment
  service_name          = "catalog-service"
  
  engine                = "postgres"
  engine_version        = "15.4"
  instance_class        = var.rds_instance_class
  allocated_storage     = var.rds_allocated_storage
  
  database_name         = "catalog_service_db"
  master_username       = "admin"
  
  vpc_id                = module.vpc.vpc_id
  subnet_ids            = module.vpc.private_subnet_ids
  vpc_cidr_blocks       = [var.vpc_cidr]
  
  backup_retention_period = var.rds_backup_retention
  multi_az               = var.rds_multi_az
  deletion_protection    = var.rds_deletion_protection
}

# ElastiCache Redis
resource "aws_elasticache_subnet_group" "redis" {
  name       = "${var.project_name}-${var.environment}-redis-subnet-group"
  subnet_ids = module.vpc.private_subnet_ids
}

resource "aws_security_group" "redis" {
  name        = "${var.project_name}-${var.environment}-redis-sg"
  description = "Security group for Redis cluster"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port   = 6379
    to_port     = 6379
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-redis-sg"
  }
}

resource "aws_elasticache_replication_group" "redis" {
  replication_group_id       = "${var.project_name}-${var.environment}-redis"
  replication_group_description = "Redis cluster for Urban Company"
  engine                     = "redis"
  engine_version             = "7.0"
  node_type                  = var.redis_node_type
  num_cache_clusters         = var.redis_num_nodes
  parameter_group_name       = "default.redis7"
  port                       = 6379
  subnet_group_name          = aws_elasticache_subnet_group.redis.name
  security_group_ids         = [aws_security_group.redis.id]
  
  automatic_failover_enabled = var.redis_num_nodes > 1
  multi_az_enabled           = var.redis_num_nodes > 1
  
  at_rest_encryption_enabled = true
  transit_encryption_enabled = true
  
  snapshot_retention_limit   = var.redis_snapshot_retention
  snapshot_window            = "03:00-05:00"
  maintenance_window         = "sun:05:00-sun:07:00"

  tags = {
    Name = "${var.project_name}-${var.environment}-redis"
  }
}

# MSK (Managed Kafka)
resource "aws_msk_cluster" "kafka" {
  cluster_name           = "${var.project_name}-${var.environment}-kafka"
  kafka_version          = "3.5.1"
  number_of_broker_nodes = var.kafka_broker_nodes

  broker_node_group_info {
    instance_type   = var.kafka_instance_type
    client_subnets  = module.vpc.private_subnet_ids
    security_groups = [aws_security_group.kafka.id]

    storage_info {
      ebs_storage_info {
        volume_size = var.kafka_volume_size
      }
    }
  }

  encryption_info {
    encryption_in_transit {
      client_broker = "TLS"
      in_cluster    = true
    }
    encryption_at_rest_kms_key_arn = aws_kms_key.kafka.arn
  }

  configuration_info {
    arn      = aws_msk_configuration.kafka.arn
    revision = aws_msk_configuration.kafka.latest_revision
  }

  logging_info {
    broker_logs {
      cloudwatch_logs {
        enabled   = true
        log_group = aws_cloudwatch_log_group.kafka.name
      }
    }
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-kafka"
  }
}

resource "aws_security_group" "kafka" {
  name        = "${var.project_name}-${var.environment}-kafka-sg"
  description = "Security group for MSK cluster"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port   = 9092
    to_port     = 9092
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  ingress {
    from_port   = 9094
    to_port     = 9094
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-kafka-sg"
  }
}

resource "aws_msk_configuration" "kafka" {
  name              = "${var.project_name}-${var.environment}-kafka-config"
  kafka_versions    = ["3.5.1"]
  server_properties = <<PROPERTIES
auto.create.topics.enable=true
default.replication.factor=3
min.insync.replicas=2
num.partitions=3
log.retention.hours=168
PROPERTIES
}

resource "aws_kms_key" "kafka" {
  description             = "KMS key for Kafka encryption"
  deletion_window_in_days = 10

  tags = {
    Name = "${var.project_name}-${var.environment}-kafka-kms"
  }
}

resource "aws_cloudwatch_log_group" "kafka" {
  name              = "/aws/msk/${var.project_name}-${var.environment}"
  retention_in_days = 7

  tags = {
    Name = "${var.project_name}-${var.environment}-kafka-logs"
  }
}

# S3 Bucket for application assets
resource "aws_s3_bucket" "app_assets" {
  bucket = "${var.project_name}-${var.environment}-assets"

  tags = {
    Name = "${var.project_name}-${var.environment}-assets"
  }
}

resource "aws_s3_bucket_versioning" "app_assets" {
  bucket = aws_s3_bucket.app_assets.id

  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "app_assets" {
  bucket = aws_s3_bucket.app_assets.id

  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

# CloudFront distribution for static assets
resource "aws_cloudfront_distribution" "assets" {
  enabled             = true
  is_ipv6_enabled     = true
  comment             = "Urban Company Assets CDN"
  default_root_object = "index.html"

  origin {
    domain_name = aws_s3_bucket.app_assets.bucket_regional_domain_name
    origin_id   = "S3-${aws_s3_bucket.app_assets.id}"

    s3_origin_config {
      origin_access_identity = aws_cloudfront_origin_access_identity.assets.cloudfront_access_identity_path
    }
  }

  default_cache_behavior {
    allowed_methods  = ["GET", "HEAD", "OPTIONS"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = "S3-${aws_s3_bucket.app_assets.id}"

    forwarded_values {
      query_string = false
      cookies {
        forward = "none"
      }
    }

    viewer_protocol_policy = "redirect-to-https"
    min_ttl                = 0
    default_ttl            = 3600
    max_ttl                = 86400
    compress               = true
  }

  restrictions {
    geo_restriction {
      restriction_type = "none"
    }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-cdn"
  }
}

resource "aws_cloudfront_origin_access_identity" "assets" {
  comment = "OAI for Urban Company assets"
}
