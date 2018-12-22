http://localhost:8080/crtr/health

docker:
docker build -f src/main/docker/Dockerfile -t crtr .
docker build -f src/main/docker/Dockerfile -t 0169...../ecs-example-repository:crtr .

docker run -d -p 8080:8080 crtr 

with docker for windows
http://10.0.75.1:8080/crtr/health

aws configure
$(aws ecr get-login --no-include-email --region eu-central-1)
 docker push 0169...eu-west-3.amazonaws.com/ecs-repository:crtr 