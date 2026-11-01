package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.TransaksiPinjam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Unit test pembentukan Bukti dan exception alur alternatif UC-01. */
class BuktiTest {

    @Test
    void bukti_dari_transaksi_menyalin_data() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        TransaksiPinjam t = TransaksiPinjam.buat(anggota, new Buku("B001", "Pemrograman Java", 3));

        Bukti bukti = Bukti.dari(t);

        assertEquals(t.getIdTransaksi(), bukti.getIdTransaksi());
        assertEquals("Budi Santoso", bukti.getNamaAnggota());
        assertEquals("Pemrograman Java", bukti.getJudulBuku());
        assertEquals("DIPINJAM", bukti.getStatus());
    }

    @Test
    void hanya_stok_habis_yang_menawarkan_reservasi() {
        assertTrue(PinjamGagalException.stokHabis().isBisaReservasi());
        assertFalse(PinjamGagalException.melewatiBatas().isBisaReservasi());
        assertEquals("Buku tidak ditemukan",
                PinjamGagalException.tidakDitemukan("Buku").getMessage());
    }
}
