package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

public record DatabaseConfig(
    String host,
    int port,
    String databaseName,
    String username,
    String password,
    String sslMode) {

  public DatabaseConfig(String host, int port, String databaseName, String username, String password) {
    this(host, port, databaseName, username, password, null);
  }

  public String buildJdbcUrl() {
    if (port == 5432 || port == 6543) {
      return String.format("jdbc:postgresql://%s:%d/%s?sslmode=require", host, port, databaseName);
    }
    if (sslMode != null && !sslMode.isBlank() && !"DISABLED".equalsIgnoreCase(sslMode)) {
      return String.format(
          "jdbc:mysql://%s:%d/%s?sslMode=%s&serverTimezone=UTC&allowPublicKeyRetrieval=true",
          host, port, databaseName, sslMode);
    }
    if (port == 3306) {
      return String.format(
          "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
          host, port, databaseName);
    }
    return String.format(
        "jdbc:mysql://%s:%d/%s?useSSL=true&sslMode=REQUIRED&serverTimezone=UTC&allowPublicKeyRetrieval=true",
        host, port, databaseName);
  }
}
