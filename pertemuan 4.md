# Pertemuan 4
## Langkah 0
1. Buka XAMPP
2. Nyalain MySQL
3. Nyalain Apache
4. Buka PHPMyAdmin
5. Buat database baru namanya hibernate_db
## Langkah 1
1. New Project -> Java With Maven -> Java Application
2. Tambahin Code dibawah di project files -> pom.xml
```XML
<dependencies>
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.6.0.Final</version>
        </dependency>

        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
    </dependencies>
    <build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>false</filtering>
        </resource>
    </resources>
```
1. Click kanan project - > Build (Harusnya dependancy nya udh nambah)
2. Source Packages -> new package 
```java
com.mahasiswa.controller
    MahasiswaController.java (java interface)
    MahasiswaControllerImpl.java
com.mahasiswa.model
    HibernateUtil.java
    ModelMahasiswa.java
    ModelTabelMahasiswa.java
com.mahasiswa.view
    MahasiswaView.java
```
## Langkah 2
Pada `ModelMahasiswa` buat dulu dibawah ini
```java
    private int id;
    private String npm;
    private String nama;
    private int semester;
    private float ipk;
```
Source (di atas) -> Insert Code -> Setter and Getter
trus buat Constructor
```
 public ModelMahasiswa(int id, String npm, String nama, int semester, float ipk) {
        this.id = id;
        this.npm = npm;
        this.nama = nama;
        this.semester = semester;
        this.ipk = ipk;
    }
```
**Masukkan Notasi Hibernate dan Buat constructor kosong untuk ORM nya**
```java
package com.mahasiswa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class ModelMahasiswa {
    // Pertama buat deklarasi dulu ini semua

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

    //Buka code -> generate -> constructor -> all
    //code -> generate -> setter and getter -> All
    //Baru buat anotasi @ nya

   
    
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
}
```

**Buka** `HibernateUtil.java` trus bikin masukin di dalemmnya 
```java
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void testConnection() {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Connection to the database was successful!");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}

```

## Langkah 3
pada `MahasiswaController.java`(interface nya)
```java
package org.mahasiswa.controller;

import org.mahasiswa.model.ModelMahasiswa;
import java.util.List;

public interface MahasiswaController {
    public void addMhs(ModelMahasiswa mhs);
    public ModelMahasiswa getMhs(int id);
    public void updateMhs(ModelMahasiswa mhs);
    public void deleteMhs(int id);
    public List<ModelMahasiswa> getAllMahasiswa();
}
```
## Langkah 4
pada `MahasiswaControllerImpl.java` (implementasi nya) `public class MahasiswaControllerImpl implements MahasiswaController` -> Lampu kuning di click trus implements all abstract method
```java
 @Override
    public void addMhs(ModelMahasiswa mhs){
        Transaction trx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            session.save(mhs);
            trx.commit();
        }catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void updateMhs(ModelMahasiswa mhs) {
        Transaction trx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            session.update(mhs);
            trx.commit();
        } catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void deleteMhs(int id) {
        Transaction trx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            ModelMahasiswa mhs = session.get(ModelMahasiswa.class, id);
            if(mhs != null){
                session.delete(mhs);
                System.out.println("Berhasil hapus");
            }
            trx.commit();
        } catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<ModelMahasiswa> getAllMahasiswa() {
        Transaction trx = null;
        List<ModelMahasiswa> listMhs = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            // Using HQL (Hibernate Query Language) to fetch all records
            Query<ModelMahasiswa> query = session.createQuery("from ModelMahasiswa", ModelMahasiswa.class);
            listMhs = query.list(); // Fetch all results

            trx.commit(); // Commit transaction
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback(); // Rollback transaction in case of error
            }
            e.printStackTrace();
        }

        // Return the fetched list
        return listMhs;
    }

    @Override
    public ModelMahasiswa getMhs(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
```

## Langkah 5
Kita buat view pada `com.mahasiswa.view` ->
`MahasiswaView.java` (BENTUK NYA **JFrame Form**)
 1. Buat field NPM, Nama, Semester, IPK
 2. Buat button Simpan dan Refresh
 3. Ganti nama Variabel (namaField, npmField, ipkField, semesterField, refreshButton, simpanButton)
 4. tambahin JTable -> variabel = dataTable
MahasiswaView adalah JFrameForm, pada file MahasiswaView buatlah design seperti ini

![MahasiswaView](https://drive.google.com/uc?id=1nr-kLWcUvH1rQWyhg83MDxcQmzv38CK5)

## Langkah 6
Kita edit `ModelTabelMahasiswa.java` (fungsi nya si model tabel itu buat manggil data di database trus jadiin sebuah list, nah biar nanti bisa dipanggil di view nya)

`public class ModelTabelMahasiswa extends AbstractTableModel {`
```java
private List<ModelMahasiswa> mahasiswaList;
    private String[] columnNames = {"ID", "NPM", "Nama", "Semester", "IPK"};

    public ModelTabelMahasiswa(List<ModelMahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @Override
    public int getRowCount() {
        return mahasiswaList.size(); // Jumlah baris sesuai dengan jumlah data mahasiswa
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Jumlah kolom sesuai dengan jumlah elemen dalam columnNames
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelMahasiswa mahasiswa = mahasiswaList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return mahasiswa.getId();
            case 1:
                return mahasiswa.getNpm();
            case 2:
                return mahasiswa.getNama();
            case 3:
                return mahasiswa.getSemester();
            case 4:
                return mahasiswa.getIpk();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Mengatur nama kolom
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Semua sel tidak dapat diedit
    }

    // Method untuk menambahkan atau memodifikasi data, jika dibutuhkan
    public void setMahasiswaList(List<ModelMahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
        fireTableDataChanged(); // Memberitahu JTable bahwa data telah berubah
    }
```

## Langkah 7
pada `MahasiswaView.java`
```java
public class MahasiswaView2 extends javax.swing.JFrame {

    /**
     * Creates new form MahasiswaView2
     */
    private MahasiswaControllerImpl controller;
    public MahasiswaView2() {
        initComponents();
        controller = new MahasiswaControllerImpl();
        HibernateUtil.testConnection();
        loadMahasiswaTable();
    }
    public void loadMahasiswaTable() {
    // Ambil data dari controller
    List<ModelMahasiswa> listMahasiswa = controller.getAllMahasiswa();

    // Buat model tabel kustom dengan data mahasiswa
    ModelTabelMahasiswa tableModel = new ModelTabelMahasiswa(listMahasiswa);

    // Set model pada JTable
    dataTable.setModel(tableModel);
}
```
Selanjutnya
1. Click 2x tombol simpan
    ```java
            String npm = getNpmField().getText();
            String nama = getNamaField().getText();
            int semester = Integer.parseInt(getSemesterField().getText());
            float ipk = Float.parseFloat(getIpkField().getText());
            ModelMahasiswa mahasiswa = new ModelMahasiswa(0, npm, nama, semester, ipk);
            System.out.println(mahasiswa.getIpk());
            System.out.println(mahasiswa.getNama());
            System.out.println(mahasiswa.getSemester());
            System.out.println(mahasiswa.getNpm());
            
            controller.addMhs(mahasiswa);
            loadMahasiswaTable();
    ```
2. Click 2x tombol refresh
   ```java
   loadMahasiswaTable();
   ```
3. Click 2x tombol buang
    ```java
    // TODO add your handling code here:
        // Membuat JTextField untuk memasukkan ID
        JTextField idField = new JTextField(5);

        // Membuat panel untuk menampung JTextField
        JPanel panel = new JPanel();
        panel.add(new JLabel("Masukkan ID yang ingin dihapus:"));
        panel.add(idField);

        // Menampilkan dialog box dengan JTextField, tombol OK, dan Cancel
        int result = JOptionPane.showConfirmDialog(null, panel, 
            "Hapus Mahasiswa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Jika tombol OK ditekan
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Mengambil input ID dan memanggil metode deleteMhs
                int id = Integer.parseInt(idField.getText());
                controller.deleteMhs(id);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                // Menangani error jika ID yang dimasukkan bukan angka
                JOptionPane.showMessageDialog(null, "ID harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    ```   

## Langkah 8
Buatlah sebuah file bernama `hibernate.cfg.xml` pada src -> main -> resources (kalo gaada silahkan dibuat folder nya) **ganti hibernate_db dengan nama db masing masing**
```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/hibernate_db</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password"></property>

        <!-- JDBC connection pool settings -->
        <property name="hibernate.c3p0.min_size">5</property>
        <property name="hibernate.c3p0.max_size">20</property>
        <property name="hibernate.c3p0.timeout">300</property>
        <property name="hibernate.c3p0.max_statements">50</property>
        <property name="hibernate.c3p0.idle_test_period">3000</property>

        <!-- SQL dialect -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

        <!-- Echo all executed SQL to stdout -->
        <property name="hibernate.show_sql">true</property>

        <!-- Drop and re-create the database schema on startup -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Mapping class -->
        <mapping class="com.mahasiswa.model.ModelMahasiswa"/>
    </session-factory>
</hibernate-configuration>

```

## Langkah 9
lakukan running pada MahasiswaView
