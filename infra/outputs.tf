output "rds_endpoint" {
  description = "Endpoint para colocar no application.properties"
  value       = aws_db_instance.proposta.endpoint
}

output "rds_port" {
  value = aws_db_instance.proposta.port
}
