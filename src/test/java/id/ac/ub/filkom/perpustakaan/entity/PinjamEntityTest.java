package id.ac.ub.filkom.perpustakaan.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Unit test method entity yang dipanggil control pada sequence UC-01. */
class PinjamEntityTest {

    @Test
    void cekStok_dan_kurangiStok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 1);
        assertTrue(buku.cekStok());
        buku.kurangiStok();
        assertEquals(0, buku.getStok());
        assertFalse(buku.cekStok());
        assertThrows(IllegalStateException.class, buku::kurangiStok);
    }

    @Test
    void cekBatasPinjam_menolak_jika_sudah_mencapai_batas() {
        Anggota anggota = new Anggota("A002", "Sari Dewi", 1);
        Buku buku = new Buku("B003", "Basis Data", 2);
        assertTrue(anggota.cekBatasPinjam());

        TransaksiPinjam t = TransaksiPinjam.buat(anggota, buku);
        assertEquals(TransaksiPinjam.DIPINJAM, t.getStatus());
        assertFalse(anggota.cekBatasPinjam());
    }

    @Test
    void cekBatasPinjam_masih_boleh_selama_di_bawah_batas() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        Buku buku = new Buku("B001", "Pemrograman Java", 3);

        TransaksiPinjam.buat(anggota, buku);
        assertTrue(anggota.cekBatasPinjam());      // baru 1 dari 2
        TransaksiPinjam.buat(anggota, buku);
        assertFalse(anggota.cekBatasPinjam());     // sudah 2 dari 2
    }

    @Test
    void buat_mengisi_data_transaksi_dengan_benar() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        Buku buku = new Buku("B001", "Pemrograman Java", 3);

        TransaksiPinjam t = TransaksiPinjam.buat(anggota, buku);

        assertSame(anggota, t.getAnggota());
        assertSame(buku, t.getBuku());
        assertEquals(LocalDate.now(), t.getTanggalPinjam());
        assertTrue(t.isDipinjam());
        // id_transaksi VARCHAR(10): "T" + 8 karakter
        assertEquals(9, t.getIdTransaksi().length());
        assertTrue(t.getIdTransaksi().startsWith("T"));
    }

    @Test
    void buku_menolak_stok_awal_negatif() {
        assertThrows(IllegalArgumentException.class, () -> new Buku("B009", "X", -1));
    }
}
