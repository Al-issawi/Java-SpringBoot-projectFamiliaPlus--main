# FAMILIA-PLUS Database Setup Guide

## Overview

This guide explains how to set up a secure PostgreSQL database for the FAMILIA-PLUS application, both locally and in production.

## Local Development Setup

### Prerequisites

1. PostgreSQL installed on your system
2. Java 21 or higher
3. Maven 3.6 or higher

### Steps

#### 1. Install PostgreSQL

- **Windows**: Download from https://www.postgresql.org/download/windows/
- **macOS**: Use Homebrew: `brew install postgresql`
- **Linux**: Use your distribution's package manager: `sudo apt-get install postgresql postgresql-contrib`

#### 2. Create Database and User

```sql
-- Connect to PostgreSQL as superuser
sudo -u postgres psql

-- Create database
CREATE DATABASE familiaplus;

-- Create user with secure password
CREATE USER familiaplus_user WITH ENCRYPTED PASSWORD 'your_secure_password_here';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE familiaplus TO familiaplus_user;
GRANT ALL ON SCHEMA public TO familiaplus_user;

-- Exit PostgreSQL
\q
```

#### 3. Set Environment Variables (Local Development)

Create a `.env` file in your project root:

```env
# Database Configuration
DATABASE_URL=jdbc:postgresql://localhost:5432/familiaplus
DATABASE_USERNAME=familiaplus_user
DATABASE_PASSWORD=your_secure_password_here

# Optional Configuration
DB_SHOW_SQL=true
DB_DDL_AUTO=update
```

#### 4. Load Environment Variables (Windows)

```powershell
# Load .env file (create a load-env.ps1 script)
Get-Content .env | foreach {
    $name, $value = $_.split('=')
    if ([string]::IsNullOrWhiteSpace($name) -or $name.Contains('#')) {
        continue
    }
    Set-Item -Path "env:$name" -Value $value
}

# Then run the application
mvn spring-boot:run
```

## Production Deployment Setup

### Railway Platform

#### 1. Create Database Service

1. Go to Railway dashboard
2. Click "New Project" → "Provision PostgreSQL"
3. Note the connection details provided

#### 2. Set Environment Variables in Railway

In your Railway project dashboard, go to Variables and set:

```
DATABASE_URL=postgresql://username:password@hostname:port/database
DATABASE_USERNAME=your_railway_db_username
DATABASE_PASSWORD=your_railway_db_password
DB_SHOW_SQL=false
DB_DDL_AUTO=update
```

#### 3. Connect Your Application

Your application will automatically use these environment variables when deployed.

### Heroku Platform

#### 1. Add PostgreSQL Add-on

```bash
heroku addons:create heroku-postgresql:hobby-dev -a your-app-name
```

#### 2. Set Additional Environment Variables

```bash
heroku config:set DB_SHOW_SQL=false -a your-app-name
heroku config:set DB_DDL_AUTO=update -a your-app-name
```

## Security Best Practices

### 1. Password Security

- Use strong, unique passwords (minimum 12 characters)
- Include uppercase, lowercase, numbers, and special characters
- Never commit passwords to version control

### 2. Environment Variables

- Use environment variables for all sensitive data
- Different passwords for development and production
- Rotate passwords regularly

### 3. Database Access

- Limit database user privileges to minimum required
- Use SSL connections in production
- Regular database backups

### 4. Application Security

```properties
# Additional security settings for application.properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# SSL settings for production
spring.datasource.hikari.data-source-properties.sslmode=require
spring.datasource.hikari.data-source-properties.sslcert=client-cert.pem
spring.datasource.hikari.data-source-properties.sslkey=client-key.pem
spring.datasource.hikari.data-source-properties.sslrootcert=ca-cert.pem
```

## Troubleshooting

### Connection Issues

1. Verify PostgreSQL is running: `systemctl status postgresql`
2. Check database exists: `psql -U familiaplus_user -d familiaplus`
3. Verify environment variables are loaded
4. Check application logs for detailed error messages

### Performance Optimization

1. Monitor connection pool usage
2. Optimize database queries
3. Regular database maintenance (VACUUM, ANALYZE)
4. Monitor database size and growth

## Testing Database Connection

### Local Test

```bash
# Test connection with psql
psql -h localhost -U familiaplus_user -d familiaplus

# Test with application
mvn spring-boot:run
# Check http://localhost:9000/actuator/health
```

### Production Test

```bash
# Check health endpoint
curl https://your-app.railway.app/health
```

## Backup and Recovery

### Local Backup

```bash
pg_dump -U familiaplus_user -h localhost familiaplus > backup.sql
```

### Restore from Backup

```bash
psql -U familiaplus_user -h localhost familiaplus < backup.sql
```

### Production Backup

Most cloud providers offer automated backup solutions. Enable them for production databases.

## Migration Considerations

When updating the database schema:

1. Always test migrations in development first
2. Backup production database before migrations
3. Use `spring.jpa.hibernate.ddl-auto=validate` in production
4. Consider using Flyway or Liquibase for complex migrations

---

## Contact

For technical support with database setup, contact the development team.
