# sé que primer comentario a veces se usa para definir formato syntax
# no me importa

# voy copiando guía del README.md (respuestas)
# y agrego notas

FROM python:3.12

# en docs oficiales primero copia req, ejecuta pip y luego copia lo demás (mejor manejo de caché)
# https://docs.docker.com/compose/gettingstarted/
# por otro lado, está .evn ignorado en .dockerignore, así que safe
COPY . .
RUN pip install -r requirements.txt


CMD ["uvicorn", "gateway:app", "--host", "0.0.0.0", "--port", "8000"]


# docker build -t gateway:v1 ./gateway
# docker run --rm -p 8000:8000 gateway:v1
