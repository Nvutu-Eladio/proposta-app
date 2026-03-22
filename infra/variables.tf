variable "aws_region" {
  default = "us-east-1"
}

variable "db_password" {
  description = "Senha do banco PostgreSQL"
  type        = string
  sensitive   = true  # ← não aparece no terraform plan/apply
}

variable "meu_ip" {
  description = "Seu IP público para liberar acesso ao RDS"
  type        = string
  # Descubra em: https://checkip.amazonaws.com
}
