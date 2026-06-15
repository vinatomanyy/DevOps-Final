FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -y \
    git \
    nginx \
    openssh-server \
    maven \
    default-mysql-client \
    php \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

RUN git clone https://github.com/vinatomanyy/DevOps-Final.git /app

WORKDIR /app

RUN printf "spring.application.name=demo\nserver.port=8080\nspring.datasource.url=jdbc:mysql://db:3306/MANY_Vinato-db\nspring.datasource.username=root\nspring.datasource.password=Hello@123\nspring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver\nspring.jpa.hibernate.ddl-auto=update\nspring.jpa.database-platform=org.hibernate.dialect.MySQLDialect\nspring.jpa.show-sql=true\n" > src/main/resources/application.properties

RUN mvn package -DskipTests

COPY nginx.conf /etc/nginx/sites-available/default
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080 22

ENTRYPOINT ["/entrypoint.sh"]
