# Pertemuan 5
## Struktur Project
```
Pertemuan5/
├── pom.xml
└── src
    └── main
       ├── java
       │   └── org
       │       └── mahasiswa
       │           ├── Pertemuan5SpringBootApplication.java
       │           ├── controller
       │           │   └── MenuController.java
       │           ├── model
       │           │   └── Mahasiswa.java
       │           └── repository
       │               └── MahasiswaRepository.java
       └── resources
           └── application.properties
```

## Langkah 0
1. Buka XAMPP
2. Nyalain MySQL
3. Nyalain Apache
4. Buka PHPMyAdmin
5. Buat database baru namanya hibernate_db


## Langkah 1
1. New Project -> Java With Maven -> Java Application
2. Tambahin Code dibawah di project files -> pom.xml
    ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.3</version>
        <relativePath/>
    </parent>

    <dependencies>
        <!-- Hibernate + Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <!-- Spring Boot Web dependency (for MVC if needed) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Testing dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    ```
## Langkahh 2
Model pada pertemuan sebelumnya kita pake, tapi kita tambah baru
```java
package com.mahasiswa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class ModelMahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "npm", nullable = false, length = 8)
    private String npm;

    @Column(name = "nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "semester")
    private int semester;

    @Column(name = "ipk")
    private float ipk;   
    
    // constructor kosong untuk ORM
    public ModelMahasiswa() {

    }
    
     //constructor ada isinya untuk dipanggil di aplikasi
    public ModelMahasiswa(int id, String npm, String nama, int semester, float ipk) {
        this.id = id;
        this.npm = npm;
        this.nama = nama;
        this.semester = semester;
        this.ipk = ipk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public float getIpk() {
        return ipk;
    }

    public void setIpk(float ipk) {
        this.ipk = ipk;
    }
    
    @Override
    public String toString() {
        return "Mahasiswa{" +
                "id=" + id +
                ", nama='" + npm + '\'' +
                ", nama='" + nama + '\'' +
                ", nama='" + semester + '\'' +
                ", jurusan='" + ipk + '\'' +
                '}';
    }
}
```
## Langkah 3
Pada package repository di file `MahasiswaRepository.java` lakukan extends
didalam nya tidak perlu dimasukkan apa apa, karena JpaRepository sudah memiliki class yang dibutuhkan
dan kita extends agar memudahkan pemanggilan fungsi repository nya
```java
package com.mahasiswa.repository;

import com.mahasiswa.model.ModelMahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends JpaRepository<ModelMahasiswa, Long> {

}
```

## Langkah 4
dibuatlah controller yang berisikan aksi yang dapat program lakukan
pada `MahasiswaController.java`
```java
package com.mahasiswa.controller;

import com.mahasiswa.model.ModelMahasiswa;
import com.mahasiswa.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class MahasiswaController {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public void tampilkanMenu() {
        Scanner scanner = new Scanner(System.in);
        int opsi;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Tampilkan semua mahasiswa");
            System.out.println("2. Tambah mahasiswa baru");
            System.out.println("3. Cek koneksi database");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            opsi = scanner.nextInt();
            scanner.nextLine(); // menangkap newline

            switch (opsi) {
                case 1:
                    tampilkanSemuaMahasiswa();
                    break;
                case 2:
                    tambahMahasiswa(scanner);
                    break;
                case 3:
                    cekKoneksi();
                    break;
                case 4:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Opsi tidak valid, coba lagi.");
            }

        } while (opsi != 4);
    }

    private void tampilkanSemuaMahasiswa() {
        List<ModelMahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        if (mahasiswaList.isEmpty()) {
            System.out.println("Tidak ada data mahasiswa.");
        } else {
            mahasiswaList.forEach(mahasiswa -> System.out.println(mahasiswa));
        }
    }

    private void tambahMahasiswa(Scanner scanner) {
        System.out.print("Masukkan NPM : ");
        String npm = scanner.nextLine();
        System.out.print("Masukkan Nama : ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Semester : ");
        int semester = scanner.nextInt();
        System.out.print("Masukkan IPK : ");
        float ipk = scanner.nextFloat();

        ModelMahasiswa mahasiswa = new ModelMahasiswa(0, npm, nama, semester, ipk);
        mahasiswaRepository.save(mahasiswa);
        System.out.println("Mahasiswa berhasil ditambahkan.");
    }

    private void cekKoneksi() {
        try {
            mahasiswaRepository.findAll();
            System.out.println("Koneksi ke database berhasil.");
        } catch (Exception e) {
            System.out.println("Gagal terhubung ke database.");
        }
    }
}
```

## Langkah 5
Langkah terakhir adalah membuat file baru berjudul application.properties pada resources dengan isi dibawah
```properties
# Konfigurasi MySQL Hibernate
spring.datasource.url=jdbc:mysql://localhost:3306/cobaspring_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
sesuaikan nama database sesuai dengan database yang dibuat