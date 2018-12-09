FROM openjdk:8-alpine
COPY ./target/classes /app/classes
COPY ./target/lib /app/lib
WORKDIR /app
RUN cd /app
CMD java -cp "./classes/:./lib/*" tools.FlightBooker