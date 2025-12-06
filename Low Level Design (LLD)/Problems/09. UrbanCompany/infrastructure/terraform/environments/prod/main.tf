terraform {
  backend "s3" {
    bucket         = "urban-company-terraform-state"
    key            = "prod/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-state-lock"
  }
}

module "infrastructure" {
  source = "../.."
}
