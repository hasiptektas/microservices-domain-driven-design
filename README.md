# Instagram Benzeri Uygulamanın DDD ve Mikroservis Mimarisi

Bu proje, ödevde belirtilen Domain-Driven Design (DDD) prensiplerine göre tasarlanmış Instagram benzeri bir uygulamanın mikroservis mimarisini içermektedir.

## 📁 Proje Yapısı

```
ms-dd/
├── user-service/           # Kullanıcı yönetimi servisi
├── post-service/           # Gönderi yönetimi servisi
├── feed-service/           # Ana sayfa akışı servisi
├── interaction-service/    # Etkileşim servisi (beğeni, yorum, takip)
├── notification-service/   # Bildirim servisi
├── media-service/          # Medya işleme servisi
├── tagging-service/        # Etiketleme servisi (hashtag, mention)
├── docker-compose.yml      # Tüm servisleri orchestrate eden dosya
├── nginx.conf             # API Gateway konfigürasyonu
├── prometheus.yml         # Monitoring konfigürasyonu
├── README.md              # Bu dosya
└── DEGERLENDIRME.md       # Proje değerlendirmesi
```

## 🏗️ DDD Paket Yapısı

Her mikroservis aşağıdaki DDD paket yapısını takip eder:

```
src/main/java/com/example/{service}/
├── domain/
│   ├── model/          # Entities, Value Objects, Aggregates
│   ├── service/        # Domain Services
│   └── event/          # Domain Events
├── repository/         # Repository Interfaces  
├── application/        # Application Services (Command/Query Handlers)
├── infrastructure/     # Infrastructure Implementations
└── api/               # REST Controllers
```

## 🎯 Bounded Context'ler

### 1. User Service (Kullanıcı Yönetimi Context'i)
- **Sorumluluklar**: Kullanıcı kayıt, giriş, profil güncelleme, takip işlemleri
- **Domain Entities**: User, Email, Username
- **Domain Services**: PasswordService
- **Domain Events**: UserCreatedEvent
- **Port**: 8081

### 2. Post Service (Gönderi & Medya Context'i)  
- **Sorumluluklar**: Gönderi oluşturma, silme, caption düzenleme
- **Domain Entities**: Post, Comment, MediaFile
- **Domain Services**: TaggingService
- **Domain Events**: PostCreatedEvent, UserMentionedEvent
- **Port**: 8082

### 3. Feed Service (Etkileşim & Akış Context'i)
- **Sorumluluklar**: Ana sayfa akışı oluşturma, trending content, caching
- **Domain Models**: FeedItem
- **Domain Services**: FeedGenerationService
- **Özellikler**: Kişiselleştirilmiş akış, Redis caching, trend algoritması
- **Port**: 8083

### 4. Interaction Service (Etkileşim Context'i)
- **Sorumluluklar**: Beğeni, yorum, paylaşım, takip işlemleri
- **Domain Entities**: Like, Share, Follow
- **Domain Events**: PostLikedEvent, UserFollowedEvent
- **Port**: 8084

### 5. Notification Service (Bildirim Context'i)
- **Sorumluluklar**: Bildirim gönderme, yönetme, real-time notifications
- **Domain Entities**: Notification
- **Domain Events**: NotificationSentEvent
- **Özellikler**: WebSocket desteği, Kafka event consumption
- **Port**: 8085

### 6. Media Service (Medya Context'i)
- **Sorumluluklar**: Medya dosyası yükleme, işleme, yönetme
- **Domain Entities**: MediaFile
- **Domain Services**: S3Service (simulated)
- **Özellikler**: Image processing, thumbnail generation
- **Port**: 8086

### 7. Tagging Service (Etiketleme Context'i)
- **Sorumluluklar**: Hashtag ve mention işleme, trend analizi
- **Domain Entities**: Tag
- **Domain Services**: TaggingCommandHandler
- **Özellikler**: NLP desteği, regex-based extraction
- **Port**: 8087

## 🔄 Mikroservisler Arası İlişkiler

### Event-Driven Architecture
- **PostCreatedEvent**: Post Service → Feed Service, Notification Service
- **UserMentionedEvent**: Tagging Service → Notification Service
- **UserFollowedEvent**: User Service → Notification Service
- **PostLikedEvent**: Interaction Service → Notification Service
- **Kafka** kullanılarak asenkron iletişim

### Context Map
- **User Service ↔ Post Service**: Customer/Supplier
- **Post Service → Notification Service**: Event Publisher/Subscriber  
- **Interaction Service ↔ Feed Service**: Shared Kernel
- **Media Service**: Bağımsız, Post Service tarafından çağrılır
- **Tagging Service**: Bağımsız, Post Service ile entegre

## 🛠️ Kullanılan DDD Pattern'leri

### Tactical Design Patterns
- **Entities**: User, Post, Comment, Notification, MediaFile, Tag
- **Value Objects**: Email, Username, FeedItem
- **Aggregates**: Post Aggregate (Post + Comments), User Aggregate
- **Domain Services**: PasswordService, TaggingService, FeedGenerationService, S3Service
- **Domain Events**: PostCreatedEvent, UserCreatedEvent, UserMentionedEvent, PostLikedEvent
- **Repositories**: UserRepository, PostRepository, FeedRepository, MediaRepository

### Strategic Design Patterns  
- **Bounded Context**: Her mikroservis kendi bounded context'i
- **Ubiquitous Language**: Story, Reel, Like, Follow, Mention, Feed, Notification, Media
- **Context Mapping**: Partnership, Shared Kernel, Customer/Supplier

## 🚀 Mikroservis Mimarisi Özellikleri

### Database per Service
- **User Service**: user-db (PostgreSQL)
- **Post Service**: post-db (PostgreSQL)
- **Interaction Service**: interaction-db (PostgreSQL)
- **Notification Service**: notification-db (PostgreSQL)
- **Media Service**: media-db (PostgreSQL)
- **Tagging Service**: tagging-db (PostgreSQL)
- **Feed Service**: Redis (Cache)

### API Composition
- **NGINX API Gateway**: Port 80, request routing ve load balancing
- **Service Discovery**: Docker Compose networking
- **Rate Limiting**: NGINX ile API throttling

### CQRS (Command Query Responsibility Segregation)
- **Command Side**: CommandHandler'lar (Create, Update, Delete)
- **Query Side**: Repository interface'leri (Get, List, Search)
- **Event Sourcing**: Domain events ile state changes

## 📊 Ölçeklenebilirlik

### Bağımsız Ölçekleme
- **Media Service**: Yüksek bant genişliği ihtiyacı
- **Feed Service**: Yoğun okuma trafiği, Redis caching
- **Post Service**: Orta seviye yazma/okuma trafiği
- **Notification Service**: Push notification yoğunluğu
- **User Service**: Authentication/authorization yoğunluğu

### Performans Optimizasyonları
- **Caching**: Redis ile frequently accessed data
- **CDN**: Media files için content delivery (simulated)
- **Load Balancing**: NGINX ile horizontal scaling
- **Event Streaming**: Kafka ile async processing

## 🔧 Teknoloji Stack'i

### Backend
- **Java 17**: Domain logic ve business rules
- **Spring Boot 3.2.0**: Mikroservis framework
- **Spring Data JPA**: Database access
- **Spring Security**: Authentication/authorization
- **Spring Kafka**: Event streaming platform
- **Spring Data Redis**: Caching layer

### Database & Storage
- **PostgreSQL**: User, Post, Notification, Interaction, Media, Tagging data
- **Redis**: Feed caching ve session management
- **AWS S3**: Media storage (simulated locally)

### Infrastructure
- **Docker**: Containerization
- **Docker Compose**: Orchestration
- **NGINX**: API Gateway
- **Apache Kafka**: Event streaming
- **Prometheus**: Metrics collection
- **Grafana**: Monitoring visualization

## 📋 API Endpoints Örnekleri

### User Service (Port 8081)
```
POST /api/users/register     # Kullanıcı kaydı
POST /api/users/login        # Kullanıcı girişi
GET  /api/users/{userId}     # Profil görüntüleme
PUT  /api/users/{userId}     # Profil güncelleme
POST /api/users/{userId}/follow/{targetId}  # Takip etme
```

### Post Service (Port 8082)
```
POST /api/posts              # Yeni gönderi
GET  /api/posts/{postId}     # Gönderi detayı
PUT  /api/posts/{postId}     # Caption güncelleme
DELETE /api/posts/{postId}   # Gönderi silme
POST /api/posts/{postId}/comments  # Yorum ekleme
```

### Feed Service (Port 8083)
```
GET /api/feed/user/{userId}  # Kullanıcı akışı
GET /api/feed/trending       # Trend gönderiler
GET /api/feed/recommended/{userId}  # Önerilen içerik
GET /api/feed/explore        # Keşfet sayfası
```

### Interaction Service (Port 8084)
```
POST /api/interactions/like/{postId}     # Beğeni
DELETE /api/interactions/like/{postId}   # Beğeniyi kaldır
POST /api/interactions/share/{postId}    # Paylaş
GET /api/interactions/count/{postId}     # Etkileşim sayıları
```

### Notification Service (Port 8085)
```
GET /api/notifications/{userId}          # Bildirimleri listele
PUT /api/notifications/{id}/read         # Okundu olarak işaretle
DELETE /api/notifications/{id}           # Bildirimi sil
```

### Media Service (Port 8086)
```
POST /api/media/upload                   # Medya yükle
GET /api/media/{mediaId}                 # Medya bilgisi
GET /api/media/{mediaId}/download        # Medya indir
DELETE /api/media/{mediaId}              # Medya sil
```

### Tagging Service (Port 8087)
```
POST /api/tagging/extract                # Etiket çıkar
GET /api/tagging/trending                # Trend hashtag'ler
GET /api/tagging/search/{query}          # Etiket ara
POST /api/tagging/follow/{tagId}         # Etiketi takip et
```

## 🏃‍♂️ Çalıştırma

### Docker Compose ile Tüm Sistemi Başlatma
```bash
# Tüm servisleri başlat
docker-compose up -d

# Servisleri durdur
docker-compose down

# Logları görüntüle
docker-compose logs -f
```

### Tekil Servisleri Geliştirme Modunda Çalıştırma
```bash
# User Service
cd user-service
mvn spring-boot:run

# Post Service  
cd post-service
mvn spring-boot:run

# Feed Service
cd feed-service
mvn spring-boot:run
```

### Monitoring
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **API Gateway**: http://localhost:80

## 🧪 Test Stratejisi

### Unit Tests
- Domain logic testing
- Business rule validation
- Repository implementations

### Integration Tests  
- External service communications
- Kafka event testing
- Database operations

### Contract Tests
- API contract validation
- Event schema validation

## 🔮 Gelecek Geliştirmeler

### Service Mesh
- **Istio** ile mikroservisler arası güvenli iletişim
- Traffic management ve monitoring

### Observability
- **Distributed Tracing**: Jaeger/Zipkin
- **Centralized Logging**: ELK Stack
- **Advanced Metrics**: Custom Prometheus metrics

### CI/CD Pipeline
- **Jenkins**: Automated build/test/deploy
- **Docker Registry**: Container image management
- **Blue-Green Deployment**: Zero-downtime deployments

---

Bu proje, modern mikroservis mimarisinin DDD prensipleri ile nasıl uygulanabileceğini göstermektedir. Her servis kendi bounded context'i içinde bağımsız olarak geliştirilip deploy edilebilir.
