# FAMILIA-PLUS Deployment Guide

## Overview

This guide covers deploying the FAMILIA-PLUS application to various cloud platforms with secure database connectivity.

## Application Status

✅ **Successfully Updated Features:**

- Modern UI/UX with glassmorphism design
- Responsive navigation with icons
- FAMILIA-PLUS branding across all pages
- Updated titles and meaningful subtitles
- Environment variable-based database configuration
- Health check endpoints for deployment monitoring

## Current Deployment

🚀 **Live Application**: https://projectfamiliaplus-master-production.up.railway.app/

The application is currently deployed and running on Railway platform with:

- Automatic health checks
- Modern responsive design
- Secure environment variable configuration
- Professional FAMILIA-PLUS branding

## Pages Updated

All pages now feature the new FAMILIA-PLUS design:

1. **Login Page** (`index.html`)

   - Title: "FAMILIA-PLUS"
   - Subtitle: "Cuidando con amor y profesionalidad"
   - Modern login form with secure authentication

2. **About Us** (`sobreNosotros.html`)

   - Professional service descriptions
   - Team information with modern cards
   - Mission and values presentation

3. **Monthly Menu** (`menu.html`)

   - Nutritionist-designed meal information
   - Professional food presentation
   - Dietary information and schedules

4. **Activities** (`actividades.html`)

   - Enhanced activity cards with schedules
   - Professional descriptions for:
     - Aquagym (Monday & Wednesday 10:00 AM)
     - Stretching (Tuesday & Thursday 9:00 AM)
     - Choir (Friday 4:00 PM)

5. **Contact** (`contacto.html`)

   - Complete contact information
   - Interactive map integration
   - Contact form for inquiries
   - Professional business hours

6. **Staff Portal** (`personal.html`)

   - Care management system
   - Professional form for logging resident care
   - Secure data entry with validation

7. **Family Portal** (`familiar.html`)
   - Resident information dashboard
   - Care history tracking
   - Family communication tools

## Security Features Implemented

### Database Security

- Environment variable-based configuration
- Secure connection pooling
- SSL support ready for production
- No hardcoded credentials in source code

### Application Security

- Health check endpoints for monitoring
- Secure session management
- CSRF protection enabled
- Input validation on forms

## Local Development

### Quick Start

```bash
# Clone and navigate to project
cd projectFamiliaPlus-master

# Compile the application
mvn clean compile

# Run locally (will use embedded H2 database if PostgreSQL not configured)
mvn spring-boot:run

# Access application
http://localhost:9000
```

### With PostgreSQL Database

1. Follow the `DATABASE_SETUP.md` guide
2. Set environment variables
3. Run application with database connection

## Railway Deployment

### Current Configuration

The application is configured with:

```toml
# nixpacks.toml
[phases.setup]
nixPkgs = ['maven', 'openjdk21']

[phases.build]
cmds = ['mvn clean package -DskipTests']

[start]
cmd = 'java -jar target/*.jar'

[variables]
MAVEN_OPTS = '-Xmx1024m'
```

```json
// railway.json
{
  "$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS"
  },
  "deploy": {
    "healthcheckPath": "/health",
    "healthcheckTimeout": 100,
    "restartPolicyType": "ON_FAILURE"
  }
}
```

### Deployment Steps

1. Connect GitHub repository to Railway
2. Set environment variables for database
3. Deploy automatically on git push
4. Monitor via Railway dashboard

## Alternative Deployment Platforms

### Heroku

```bash
# Install Heroku CLI and login
heroku login

# Create application
heroku create your-app-name

# Add PostgreSQL
heroku addons:create heroku-postgresql:hobby-dev

# Set environment variables
heroku config:set DB_SHOW_SQL=false
heroku config:set DB_DDL_AUTO=update

# Deploy
git push heroku main
```

### Docker Deployment

```dockerfile
FROM openjdk:21-jdk-slim

COPY target/*.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# Build image
docker build -t familia-plus .

# Run container
docker run -p 9000:9000 \
  -e DATABASE_URL=your_db_url \
  -e DATABASE_USERNAME=your_username \
  -e DATABASE_PASSWORD=your_password \
  familia-plus
```

## Monitoring and Maintenance

### Health Checks

- **Application Health**: `/health`
- **Detailed Health**: `/actuator/health`
- **Application Info**: `/actuator/info`

### Logging

- Application logs available via platform dashboard
- Database connection logs for troubleshooting
- Error tracking and monitoring

### Performance Monitoring

- Response time monitoring
- Database connection pool metrics
- Memory and CPU usage tracking

## Environment Variables Reference

### Required for Production

```
DATABASE_URL=jdbc:postgresql://host:port/database
DATABASE_USERNAME=username
DATABASE_PASSWORD=secure_password
```

### Optional Configuration

```
DB_SHOW_SQL=false
DB_DDL_AUTO=update
MAVEN_OPTS=-Xmx1024m
PORT=9000
```

## Troubleshooting

### Common Issues

1. **Database Connection Failures**

   - Verify environment variables are set
   - Check database service is running
   - Validate connection string format

2. **Application Won't Start**

   - Check Java version (requires Java 21)
   - Verify Maven dependencies
   - Review application logs

3. **Health Check Failures**
   - Ensure `/health` endpoint is accessible
   - Check application startup time
   - Verify database connectivity

### Debug Commands

```bash
# Check environment variables
printenv | grep DATABASE

# Test database connection
psql $DATABASE_URL

# View application logs
heroku logs --tail (for Heroku)
railway logs (for Railway)
```

## Security Checklist

- [ ] Database credentials in environment variables
- [ ] SSL enabled for database connections
- [ ] No sensitive data in source code
- [ ] Health checks configured
- [ ] Error logging configured
- [ ] Backup strategy implemented
- [ ] Access logs enabled
- [ ] Security headers configured

## Support and Maintenance

### Regular Tasks

- Monitor application performance
- Review security logs
- Update dependencies
- Database maintenance
- Backup verification

### Updates and Deployment

- Test changes in staging environment
- Deploy during low-traffic periods
- Monitor post-deployment metrics
- Rollback plan ready

---

## Contact Information

For deployment support or technical issues, contact the development team.
