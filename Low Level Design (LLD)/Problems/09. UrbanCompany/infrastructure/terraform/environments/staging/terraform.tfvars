environment = "staging"

# VPC Configuration
vpc_cidr             = "10.1.0.0/16"
private_subnet_cidrs = ["10.1.1.0/24", "10.1.2.0/24", "10.1.3.0/24"]
public_subnet_cidrs  = ["10.1.101.0/24", "10.1.102.0/24", "10.1.103.0/24"]

# Multi-AZ NAT for better availability
single_nat_gateway = false

# EKS Configuration
eks_cluster_version = "1.28"
eks_node_groups = {
  general = {
    desired_size   = 3
    max_size       = 8
    min_size       = 2
    instance_types = ["t3.large"]
    capacity_type  = "ON_DEMAND"
    disk_size      = 50
  }
  compute = {
    desired_size   = 2
    max_size       = 6
    min_size       = 1
    instance_types = ["t3.xlarge"]
    capacity_type  = "SPOT"
    disk_size      = 80
  }
}

# RDS Configuration
rds_instance_class      = "db.t3.medium"
rds_allocated_storage   = 50
rds_backup_retention    = 3
rds_multi_az            = true
rds_deletion_protection = true

# Redis Configuration
redis_node_type          = "cache.t3.small"
redis_num_nodes          = 2
redis_snapshot_retention = 3

# Kafka Configuration
kafka_broker_nodes  = 3
kafka_instance_type = "kafka.m5.large"
kafka_volume_size   = 100
