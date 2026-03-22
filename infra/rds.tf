# ──────────────────────────────────────────────
# Security Group — só permite conexão do seu IP
# ──────────────────────────────────────────────
resource "aws_security_group" "rds" {
  name        = "proposta-rds-sg"
  description = "Acesso ao RDS apenas do IP local de desenvolvimento"

  ingress {
    description = "PostgreSQL do meu IP"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["${var.meu_ip}/32"]  # ← só você acessa
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# ──────────────────────────────────────────────
# RDS PostgreSQL
# ──────────────────────────────────────────────
resource "aws_db_instance" "proposta" {
  identifier        = "propostadb"
  engine            = "postgres"
  engine_version    = "16"
  instance_class    = "db.t3.micro"  # ← free tier elegível
  allocated_storage = 20
  storage_type      = "gp2"

  db_name  = "propostadb"
  username = "postgres"
  password = var.db_password

  # ↓ NECESSÁRIO para acessar do seu computador local
  publicly_accessible = true
  vpc_security_group_ids = [aws_security_group.rds.id]

  # Boas práticas
  backup_retention_period = 7       # 7 dias de backup automático
  deletion_protection     = false   # mude para true em produção
  skip_final_snapshot     = true    # mude para false em produção

  tags = {
    Name        = "proposta-app-db"
    Environment = "dev"
  }
}
