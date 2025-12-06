output "vpc_id" {
  description = "VPC ID"
  value       = module.vpc.vpc_id
}

output "vpc_cidr" {
  description = "VPC CIDR block"
  value       = module.vpc.vpc_cidr
}

output "private_subnet_ids" {
  description = "Private subnet IDs"
  value       = module.vpc.private_subnet_ids
}

output "public_subnet_ids" {
  description = "Public subnet IDs"
  value       = module.vpc.public_subnet_ids
}

# EKS Outputs
output "eks_cluster_id" {
  description = "EKS cluster ID"
  value       = module.eks.cluster_id
}

output "eks_cluster_endpoint" {
  description = "EKS cluster endpoint"
  value       = module.eks.cluster_endpoint
}

output "eks_cluster_security_group_id" {
  description = "EKS cluster security group ID"
  value       = module.eks.cluster_security_group_id
}

output "eks_cluster_certificate_authority_data" {
  description = "EKS cluster certificate authority data"
  value       = module.eks.cluster_certificate_authority_data
  sensitive   = true
}

output "configure_kubectl" {
  description = "Command to configure kubectl"
  value       = "aws eks update-kubeconfig --region ${var.aws_region} --name ${module.eks.cluster_id}"
}

# RDS Outputs
output "rds_user_service_endpoint" {
  description = "User Service RDS endpoint"
  value       = module.rds_user_service.db_instance_endpoint
}

output "rds_booking_service_endpoint" {
  description = "Booking Service RDS endpoint"
  value       = module.rds_booking_service.db_instance_endpoint
}

output "rds_payment_service_endpoint" {
  description = "Payment Service RDS endpoint"
  value       = module.rds_payment_service.db_instance_endpoint
}

output "rds_partner_service_endpoint" {
  description = "Partner Service RDS endpoint"
  value       = module.rds_partner_service.db_instance_endpoint
}

output "rds_catalog_service_endpoint" {
  description = "Catalog Service RDS endpoint"
  value       = module.rds_catalog_service.db_instance_endpoint
}

# Redis Outputs
output "redis_endpoint" {
  description = "Redis primary endpoint"
  value       = aws_elasticache_replication_group.redis.primary_endpoint_address
}

output "redis_reader_endpoint" {
  description = "Redis reader endpoint"
  value       = aws_elasticache_replication_group.redis.reader_endpoint_address
}

output "redis_port" {
  description = "Redis port"
  value       = aws_elasticache_replication_group.redis.port
}

# Kafka Outputs
output "kafka_bootstrap_brokers_tls" {
  description = "Kafka bootstrap brokers (TLS)"
  value       = aws_msk_cluster.kafka.bootstrap_brokers_tls
}

output "kafka_zookeeper_connect_string" {
  description = "Kafka Zookeeper connection string"
  value       = aws_msk_cluster.kafka.zookeeper_connect_string
}

# S3 & CloudFront Outputs
output "s3_assets_bucket_name" {
  description = "S3 bucket name for assets"
  value       = aws_s3_bucket.app_assets.id
}

output "cloudfront_domain_name" {
  description = "CloudFront distribution domain name"
  value       = aws_cloudfront_distribution.assets.domain_name
}

output "cloudfront_distribution_id" {
  description = "CloudFront distribution ID"
  value       = aws_cloudfront_distribution.assets.id
}

# Connection Strings for Services
output "connection_strings" {
  description = "Database connection strings for all services"
  value = {
    user_service = {
      endpoint = module.rds_user_service.db_instance_endpoint
      port     = module.rds_user_service.db_instance_port
      database = "user_service_db"
    }
    booking_service = {
      endpoint = module.rds_booking_service.db_instance_endpoint
      port     = module.rds_booking_service.db_instance_port
      database = "booking_service_db"
    }
    payment_service = {
      endpoint = module.rds_payment_service.db_instance_endpoint
      port     = module.rds_payment_service.db_instance_port
      database = "payment_service_db"
    }
    partner_service = {
      endpoint = module.rds_partner_service.db_instance_endpoint
      port     = module.rds_partner_service.db_instance_port
      database = "partner_service_db"
    }
    catalog_service = {
      endpoint = module.rds_catalog_service.db_instance_endpoint
      port     = module.rds_catalog_service.db_instance_port
      database = "catalog_service_db"
    }
    redis = {
      primary_endpoint = aws_elasticache_replication_group.redis.primary_endpoint_address
      reader_endpoint  = aws_elasticache_replication_group.redis.reader_endpoint_address
      port            = aws_elasticache_replication_group.redis.port
    }
    kafka = {
      bootstrap_brokers = aws_msk_cluster.kafka.bootstrap_brokers_tls
      zookeeper        = aws_msk_cluster.kafka.zookeeper_connect_string
    }
  }
  sensitive = true
}
