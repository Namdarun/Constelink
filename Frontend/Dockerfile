FROM node:18.15-alpine

WORKDIR /app

COPY ./package* ./

RUN npm install

COPY ./ ./

EXPOSE 3000

CMD ["npm", "run", "start"]
