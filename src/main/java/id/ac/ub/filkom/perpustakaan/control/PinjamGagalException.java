package id.ac.ub.filkom.perpustakaan.control;

/**
 * Alur alternatif UC-01 (stok habis, melewati batas, data tidak ditemukan).
 * RuntimeException supaya @Transactional otomatis membatalkan perubahan.
 */
public class PinjamGagalException extends RuntimeException {

    private final boolean bisaReservasi;

    private PinjamGagalException(String pesan, boolean bisaReservasi) {
        super(pesan);
        this.bisaReservasi = bisaReservasi;
    }

    /** [13] pesanHabis — stok habis, anggota boleh mengajukan reservasi («extend»). */
    public static PinjamGagalException stokHabis() {
        return new PinjamGagalException("Stok buku habis", true);
    }

    /** Alur 4b — anggota sudah mencapai batas pinjam. */
    public static PinjamGagalException melewatiBatas() {
        return new PinjamGagalException("Melewati batas pinjam", false);
    }

    /** Validasi input — id anggota/buku tidak terdaftar. */
    public static PinjamGagalException tidakDitemukan(String objek) {
        return new PinjamGagalException(objek + " tidak ditemukan", false);
    }

    public boolean isBisaReservasi() { return bisaReservasi; }
}
