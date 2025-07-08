# 📱 Calculator-App (Ứng dụng Máy tính Android) ![image](https://github.com/user-attachments/assets/bb057515-b5e3-46a0-a943-29d20303b5cf)


Một ứng dụng máy tính đơn giản dành cho Android, hỗ trợ các phép toán cơ bản (cộng, trừ, nhân, chia, phần trăm), lưu lại lịch sử tính toán bằng SQLite, giao diện đẹp theo Material Design và tích hợp thư viện máy tính popup `CalcDialogLib`.

---

## 🧠 Tính năng nổi bật

- ✅ Tính toán các phép toán cơ bản: `+`, `−`, `×`, `÷`, `%`
- ✅ Hỗ trợ số thực, dấu `.`, phím `C`, `⌫`, `=`
- ✅ Giao diện hiện đại theo phong cách Material Design
- ✅ Lưu lịch sử tính toán cục bộ bằng **SQLite**
- ✅ Hiển thị lịch sử bằng `RecyclerView`, cho phép:
  - Xem lại phép tính trước đó
  - Xóa từng phép tính hoặc toàn bộ lịch sử
- ✅ Tích hợp thư viện `CalcDialogLib` để mở máy tính dạng popup

---

## 📷 Hình ảnh giao diện
![image](https://github.com/user-attachments/assets/e029c15f-872d-422d-a7c5-a65e1425af1a)
![image](https://github.com/user-attachments/assets/665971cd-f038-4e7c-9c8a-8a3db0d6d3ff)
![image](https://github.com/user-attachments/assets/a53b931f-b336-4b9e-9231-2c6aede94513)
---

## 🔧 Công nghệ sử dụng

| Thành phần       | Công nghệ/Thư viện |
|------------------|--------------------|
| Ngôn ngữ         | Java               |
| IDE              | Android Studio     |
| Giao diện        | XML, Material Design |
| Cơ sở dữ liệu    | SQLite             |
| Hiển thị danh sách | RecyclerView      |
| Tính toán popup  | [CalcDialogLib](https://github.com/maltaisn/calcdialoglib) |
| Target SDK       | API 21+ (Android 5.0+) |

---

## 📂 Cấu trúc thư mục chính

Calculator-App/
├── java/com.example.myapplication/
│ ├── MainActivity.java
│ ├── HistoryActivity.java
│ ├── model/HistoryItem.java
│ ├── adapter/HistoryAdapter.java
│ └── database/HistoryDAO.java, HistoryDatabaseHelper.java
├── res/layout/
│ ├── activity_main.xml
│ ├── activity_history.xml
│ └── item_history.xml
├── AndroidManifest.xml
├── build.gradle
└── README.md

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## 🚀 Cách chạy ứng dụng

1. Clone repo:
   ```bash
   git clone https://github.com/thaisonlun/Calculator-App.git
2. Mở project bằng Android Studio

3. Kết nối thiết bị Android hoặc dùng emulator

4. Nhấn Run ▶ để khởi chạy ứng dụng
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Cài thư viện ngoài (CalcDialogLib)
Trong build.gradle (Module) thêm:
implementation 'com.maltaisn:calcdialog:2.2.2'

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📌 **Lưu ý**:
- Đổi link `git clone` thành link repo chính nếu bạn chuyển về `main`.
- Nếu có ảnh chụp màn hình, hãy thêm chúng vào thư mục `/assets` rồi nhúng vào phần giao diện.

Bạn có muốn mình tạo file `README.md` từ nội dung trên và gửi lại để bạn chỉ cần copy-paste vào GitHub?


