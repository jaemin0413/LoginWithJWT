# Run stage
# 이미지 생성
# 빌드 단계에서 생성된 JAR 파일만 복사
FROM bellsoft/liberica-openjdk-alpine:17-jre

WORKDIR /app

# ci.yml 의 5단계에서 이미 빌드한 jar 파일을 복사
COPY build/libs/*.jar app.jar

# 애플리케이션이 사용할 포트 번호
EXPOSE 8080

# 컨테이너 시작 시 실행될 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
