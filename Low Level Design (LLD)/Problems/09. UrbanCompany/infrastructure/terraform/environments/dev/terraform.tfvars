environment = "dev"

# VPC Configuration
vpc_cidr             = "10.0.0.0/16"
private_subnet_cidrs = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
public_subnet_cidrs  = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]

# Cost optimization for dev
single_nat_gateway = true

# EKS Configuration
eks_cluster_version = "1.28"
eks_node_groups = {
  general = {
    desired_size   = 2
    max_size       = 4
    min_size       = 1
    instance_types = ["t3.medium"]
    capacity_type  = "SPOT"
    disk_size      = 30
  }
}

# RDS Configuration - smaller instances for dev
rds_instance_class     = "db.t3.small"
rds_allocated_storage  = 20
rds_backup_retention   = 1
rds_multi_az           = false
rds_deletion_protection = false

# Redis Configuration
redis_node_type          = "cache.t3.micro"
redis_num_nodes          = 1
redis_snapshot_retention = 1

# Kafka Configuration
kafka_broker_nodes  = 2
kafka_instance_type = "kafka.t3.small"
kafka_volume_size   = 50
