-- Skema tabel dari rancangan data (8a)
CREATE TABLE anggota (
    id_anggota   VARCHAR(10)  PRIMARY KEY,
    nama         VARCHAR(100) NOT NULL,
    batas_pinjam INT          NOT NULL
);

CREATE TABLE buku (
    id_buku VARCHAR(10)  PRIMARY KEY,
    judul   VARCHAR(200) NOT NULL,
    stok    INT          NOT NULL CHECK (stok >= 0)
);

CREATE TABLE transaksi_pinjam (
    id_transaksi  VARCHAR(10) PRIMARY KEY,
    id_anggota    VARCHAR(10) NOT NULL,
    id_buku       VARCHAR(10) NOT NULL,
    tanggal_pinjam DATE       NOT NULL,
    status        VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pinjam_anggota FOREIGN KEY (id_anggota) REFERENCES anggota (id_anggota),
    CONSTRAINT fk_pinjam_buku    FOREIGN KEY (id_buku)    REFERENCES buku (id_buku)
);
