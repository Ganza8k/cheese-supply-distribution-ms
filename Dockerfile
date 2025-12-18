FROM eclipse-temurin:11-jdk

WORKDIR /app

COPY src/ ./src/

RUN javac -cp "." -d . src/model/*.java && \
    javac -cp "." -d . src/dao/*.java && \
    javac -cp "." -d . src/view/LoginFrame.java src/view/RegisterFrame.java

ENV DISPLAY=:0

CMD ["java", "-cp", ".", "view.LoginFrame"]