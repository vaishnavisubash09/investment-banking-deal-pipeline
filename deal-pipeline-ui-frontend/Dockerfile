# =========================
# Build stage
# =========================
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration production


# =========================
# Runtime stage
# =========================
FROM nginx:alpine

# Remove default nginx site config
RUN rm -f /etc/nginx/conf.d/default.conf

# Remove default html
RUN rm -rf /usr/share/nginx/html/*

# Copy Angular build
COPY --from=build /app/dist/browser /usr/share/nginx/html

# Copy our nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
