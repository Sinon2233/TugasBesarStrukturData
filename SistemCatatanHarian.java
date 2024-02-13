import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Stack;

record CatatanHarian(String tanggal, String catatan, String jadwal, boolean terealisasi) {}

public class SistemCatatanHarian {

    private static LinkedList<CatatanHarian> catatanLinkedList = new LinkedList<>();
    private static Stack<CatatanHarian> catatanStack = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selamat datang di Sistem Catatan Harian");
        System.out.println("=====================================");

        while (true) {

            System.out.println("\nMenu:");
            System.out.println("=========================");
            System.out.println(" => 1. Tambah Catatan Harian");
            System.out.println(" => 2. Lihat Catatan Harian ");
            System.out.println(" => 3. Lihat Riwayat Catatan");
            System.out.println(" => 4. Cari Catatan");
            System.out.println(" => 5. Keluar");

            System.out.print("Pilih menu (1-5): ");
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    tambahCatatan(scanner);
                    break;
                case 2:
                    lihatCatatan();
                    break;
                case 3:
                    lihatRiwayatCatatan();
                    break;
                case 4:
                    scanner.nextLine(); // Clear the newline character
                    System.out.println("\nPilih kategori pencarian:");
                    System.out.println("1. Pagi");
                    System.out.println("2. Siang");
                    System.out.println("3. Malam");
                    System.out.println("4. Berdasarkan Tanggal");
                    System.out.print("Pilih kategori (1-4): ");
                    int kategoriPencarian = scanner.nextInt();
                    if (kategoriPencarian == 4) {
                        scanner.nextLine(); // Clear the newline character
                        System.out.print("Masukkan tanggal pencarian (contoh: 2024/01/01): ");
                        String tanggalPencarian = scanner.nextLine();
                        cariCatatanBerdasarkanTanggal(tanggalPencarian);
                    } else {
                        cariCatatanBerdasarkanJadwal(kategoriPencarian);
                    }
                    break;
                case 5:
                    System.out.println("Terima kasih! Program selesai.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
            }
        }
    }

    private static void tambahCatatan(Scanner scanner) {
        scanner.nextLine(); // Clear the newline character
        System.out.print("Masukkan tanggal dan hari(contoh: senin,2024/01/01): ");
        String tanggal = scanner.nextLine();
        System.out.print("Masukkan catatan harian Anda: ");
        String catatan = scanner.nextLine();
        System.out.print("Masukkan jadwal catatan (contoh: Pagi, Siang, Malam): ");
        String jadwal = scanner.nextLine();
        System.out.print("Apakah catatan ini telah terealisasi? (true/false): ");
        boolean terealisasi = scanner.nextBoolean();
        tambahCatatan(tanggal, catatan, jadwal, terealisasi);
    }

    private static void tambahCatatan(String tanggal, String catatan, String jadwal, boolean terealisasi) {
        CatatanHarian record = new CatatanHarian(tanggal, catatan, jadwal, terealisasi);
        catatanLinkedList.add(record);
        System.out.println("Catatan berhasil ditambahkan!");
        catatanStack.push(record);
    }

    private static void lihatCatatan(){
        //urutkan catatan berdasarkan tanggal sebelum menampilkannya
        Collections.sort(catatanLinkedList, (c1, c2) -> c1.tanggal().compareTo(c2.tanggal()));

        System.out.println("\nCatatan Harian : ");
        for (CatatanHarian record : catatanLinkedList){
            tampilkanCatatan(record);
        }
    }

    private static void lihatRiwayatCatatan() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPilih kategori untuk melihat riwayat catatan:");
        System.out.println("1. Terealisasi");
        System.out.println("2. Tidak Terealisasi");
        System.out.println("3. Semua");
        System.out.print("Pilih kategori (1-3): ");
        int pilihanKategori = scanner.nextInt();

        System.out.println("\nRiwayat Catatan Harian:");
        for (CatatanHarian record : catatanLinkedList) {
            if (pilihanKategori == 1 && record.terealisasi()) {
                tampilkanCatatan(record);
            } else if (pilihanKategori == 2 && !record.terealisasi()) {
                tampilkanCatatan(record);
            } else if (pilihanKategori == 3) {
                tampilkanCatatan(record);
            }
        }
    }

    private static void tampilkanCatatan(CatatanHarian record) {
        String statusRealisasi = record.terealisasi() ? "Terealisasi" : "Tidak Terealisasi";
        System.out.println(record.tanggal() + " - " + record.jadwal() + ": " + record.catatan() + " (" + statusRealisasi + ")");
    }

    private static void cariCatatanBerdasarkanJadwal(int kategoriPencarian) {
        String jadwalPencarian = "";
        switch (kategoriPencarian) {
            case 1:
                jadwalPencarian = "Pagi";
                break;
            case 2:
                jadwalPencarian = "Siang";
                break;
            case 3:
                jadwalPencarian = "Malam";
                break;
        }

        System.out.println("\nHasil Pencarian:");
        boolean ditemukan = false;
        for (CatatanHarian record : catatanLinkedList) {
            if (record.jadwal().equalsIgnoreCase(jadwalPencarian)) {
                tampilkanCatatan(record);
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Tidak ada catatan yang sesuai dengan kriteria pencarian.");
        }
    }

    private static void cariCatatanBerdasarkanTanggal(String tanggalPencarian) {
        System.out.println("\nHasil Pencarian:");
        boolean ditemukan = false;
        for (CatatanHarian record : catatanLinkedList) {
            if (record.tanggal().equals(tanggalPencarian)) {
                tampilkanCatatan(record);
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Tidak ada catatan yang sesuai dengan kriteria pencarian.");
        }
    }
}