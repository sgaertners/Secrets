docker build -t secrets .
docker compose up
docker save secrets > secrets.tar