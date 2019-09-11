FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
RUN apk update && apk add postgresql && apk add postgresql-contrib
ENTRYPOINT ["java","-cp","app:app/lib/*","ru.yandex.assignment.employeesalary.EmployeeSalaryApplication"]