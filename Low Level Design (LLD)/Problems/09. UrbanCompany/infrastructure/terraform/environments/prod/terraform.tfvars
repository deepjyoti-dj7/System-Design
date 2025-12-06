environment = "prod"

# VPC Configuration
vpc_cidr             = "10.2.0.0/16"
private_subnet_cidrs = ["10.2.1.0/24", "10.2.2.0/24", "10.2.3.0/24"]
public_subnet_cidrs  = ["10.2.101.0/24", "10.2.102.0/24", "10.2.103.0/24"]

# High availability - NAT in each AZ
single_nat_gateway = false

# EKS Configuration
eks_cluster_version = "1.28"
eks_node_groups = {
  general = {
    desired_size   = 5
    max_size       = 15
    min_size       = 3
    instance_types = ["t3.xlarge"]
    capacity_type  = "ON_DEMAND"
    disk_size      = 100
  }
  compute = {
    desired_size   = 3
    max_size       = 12
    min_size       = 2
    instance_types = ["m5.2xlarge"]
    capacity_type  = "ON_DEMAND"
    disk_size      = 200
  }
}

# RDS Configuration - Production grade
rds_instance_class      = "db.r6g.xlarge"
rds_allocated_storage   = 200
rds_backup_retention    = 30
rds_multi_az            = true
rds_deletion_protection = true

# Redis Configuration - Cluster mode
redis_node_type          = "cache.r6g.large"
redis_num_nodes          = 3
redis_snapshot_retention = 14

# Kafka Configuration - Production cluster
kafka_broker_nodes  = 3
kafka_instance_type = "kafka.m5.2xlarge"
kafka_volume_size   = 500
