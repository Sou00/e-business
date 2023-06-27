FROM node:alpine AS development

# Declaring env
ENV REACT_APP_BACKEND_URL=https://ebusiness-back-bf7d2454a573.herokuapp.com/

# Setting up the work directory
WORKDIR /react-app

# Installing dependencies
COPY ./package.json /react-app
RUN npm install

# Copying all the files in our project
COPY . .

# Starting our application
CMD npm start