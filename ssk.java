public class ssk {
    public static void main(String[] args) {
        int n = 5;  // jumlah elemen per baris
        
        for (int i = 0; i < 5; i++) {
            // Cetak spasi di awal baris
            for (int j = 0; j < i * 2; j++) {
                System.out.print(" ");
            }
            
            if (i % 2 == 0) {
                // Jika indeks genap, cetak angka
                for (int j = 1; j <= n; j++) {
                    System.out.print(j + " ");
                }
            } else {
                // Jika indeks ganjil, cetak bintang
                for (int j = 1; j <= n; j++) {
                    System.out.print("* ");
                }
            }
            System.out.println(); // Pindah ke baris baru
        }
    }
}
