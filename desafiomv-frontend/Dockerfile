# Build da aplicação Angular
FROM node:18 AS build

WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Instala as dependências e gera a build
RUN npm install
RUN npm run build --prod

# Serve a build com Nginx
FROM nginx:alpine

# Copia os arquivos da build gerada para o diretório do Nginx
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copia a configuração customizada do Nginx
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

# Expondo a porta 80 para o frontend
EXPOSE 80
