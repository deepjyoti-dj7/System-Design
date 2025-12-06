resource "random_password" "master" {
  length  = 16
  special = true
}

resource "aws_db_subnet_group" "main" {
  name       = "${var.project_name}-${var.environment}-${var.service_name}-subnet-group"
  subnet_ids = var.subnet_ids

  tags = {
    Name = "${var.project_name}-${var.environment}-${var.service_name}-subnet-group"
  }
}

resource "aws_security_group" "main" {
  name        = "${var.project_name}-${var.environment}-${var.service_name}-rds-sg"
  description = "Security group for ${var.service_name} RDS"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = var.vpc_cidr_blocks
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-${var.service_name}-rds-sg"
  }
}

resource "aws_db_parameter_group" "main" {
  count  = length(var.parameters) > 0 ? 1 : 0
  name   = "${var.project_name}-${var.environment}-${var.service_name}-params"
  family = var.parameter_group_family

  dynamic "parameter" {
    for_each = var.parameters
    content {
      name  = parameter.value.name
      value = parameter.value.value
    }
  }

  tags = {
    Name = "${var.project_name}-${var.environment}-${var.service_name}-params"
  }
}

resource "aws_db_instance" "main" {
  identifier     = "${var.project_name}-${var.environment}-${var.service_name}"
  engine         = var.engine
  engine_version = var.engine_version
  instance_class = var.instance_class

  allocated_storage     = var.allocated_storage
  storage_type          = "gp3"
  storage_encrypted     = true
  max_allocated_storage = var.allocated_storage * 2

  db_name  = var.database_name
  username = var.master_username
  password = random_password.master.result

  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.main.id]
  parameter_group_name   = length(var.parameters) > 0 ? aws_db_parameter_group.main[0].name : null

  multi_az               = var.multi_az
  backup_retention_period = var.backup_retention_period
  backup_window          = "03:00-04:00"
  maintenance_window     = "sun:04:00-sun:05:00"

  enabled_cloudwatch_logs_exports = ["postgresql", "upgrade"]
  performance_insights_enabled    = true
  performance_insights_retention_period = 7

  deletion_protection = var.deletion_protection
  skip_final_snapshot = false
  final_snapshot_identifier = "${var.project_name}-${var.environment}-${var.service_name}-final-snapshot"

  tags = {
    Name = "${var.project_name}-${var.environment}-${var.service_name}-rds"
  }
}

# Store password in SSM Parameter Store
resource "aws_ssm_parameter" "db_password" {
  name        = "/${var.project_name}/${var.environment}/${var.service_name}/db-password"
  description = "Master password for ${var.service_name} RDS"
  type        = "SecureString"
  value       = random_password.master.result

  tags = {
    Name = "${var.project_name}-${var.environment}-${var.service_name}-db-password"
  }
}
