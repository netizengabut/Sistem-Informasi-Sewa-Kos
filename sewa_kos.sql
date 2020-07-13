-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 09 Jul 2020 pada 15.44
-- Versi Server: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sewa_kos`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kamar`
--

CREATE TABLE `kamar` (
  `no_kamar` char(10) NOT NULL,
  `type_kamar` varchar(20) NOT NULL,
  `harga_kamar` int(20) NOT NULL,
  `fasilitas` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kamar`
--

INSERT INTO `kamar` (`no_kamar`, `type_kamar`, `harga_kamar`, `fasilitas`) VALUES
('001', 'Royal', 400000, 'Non Ac, Luas Kamar 2x2,5m , Lemari,Free Wifi'),
('002', 'Premium', 650000, ' AC, Luas Kamar 4x3m, Lemari, Kamar Mandi Dalam, Free Wifi'),
('003', 'Deluxe', 800000, 'AC, Luas Kamar, 4x5m, Lemari, Kamar Mandi Dalam, Free Wifi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemilik`
--

CREATE TABLE `pemilik` (
  `Username` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pemilik`
--

INSERT INTO `pemilik` (`Username`, `password`) VALUES
('lala', 'lala');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penyewa`
--

CREATE TABLE `penyewa` (
  `id_penyewa` char(10) NOT NULL,
  `nama_penyewa` varchar(30) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL,
  `pekerjaan` varchar(15) NOT NULL,
  `no_telp` char(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penyewa`
--

INSERT INTO `penyewa` (`id_penyewa`, `nama_penyewa`, `jenis_kelamin`, `pekerjaan`, `no_telp`) VALUES
('112345', 'Langit', 'Laki - Laki', 'Karyawan', '089777888222'),
('200999', 'Jaehyun', 'Laki - Laki', 'Mahasiswa', '087654312349'),
('509327', 'Heejin', 'Perempuan', 'Mahasiswa', '081234567890'),
('543782', 'Nurul', 'Perempuan', 'Perawat', '085678927630');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tagihan`
--

CREATE TABLE `tagihan` (
  `no_transaksi` char(10) NOT NULL,
  `id_penyewa` char(10) NOT NULL,
  `nama_penyewa` varchar(25) NOT NULL,
  `no_kamar` char(10) NOT NULL,
  `type_kamar` varchar(50) NOT NULL,
  `harga_kamar` double NOT NULL,
  `bulan` varchar(10) NOT NULL,
  `tgl_bayar` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tagihan`
--

INSERT INTO `tagihan` (`no_transaksi`, `id_penyewa`, `nama_penyewa`, `no_kamar`, `type_kamar`, `harga_kamar`, `bulan`, `tgl_bayar`) VALUES
('B1234', '543782', 'Nurul', '002', 'Premium', 650000, 'JUNI', '2020-06-08');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `no_transaksi` char(20) NOT NULL,
  `id_penyewa` char(10) NOT NULL,
  `nama_penyewa` varchar(30) NOT NULL,
  `no_kamar` char(10) NOT NULL,
  `type_kamar` varchar(20) NOT NULL,
  `harga_kamar` int(20) NOT NULL,
  `tgl_masuk` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`no_transaksi`, `id_penyewa`, `nama_penyewa`, `no_kamar`, `type_kamar`, `harga_kamar`, `tgl_masuk`) VALUES
('TR0999', '543782', 'Nurul', '002', 'Premium', 650000, '2020-05-11'),
('TR1000', '112345', 'Langit', '001', 'Royal', 400000, '2020-06-01'),
('TR1001', '200999', 'Jaehyun', '002', 'Premium', 650000, '2020-06-17'),
('TR10087', '509327', 'Heejin', '003', 'Deluxe', 800000, '2020-07-01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`no_kamar`);

--
-- Indexes for table `pemilik`
--
ALTER TABLE `pemilik`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `penyewa`
--
ALTER TABLE `penyewa`
  ADD PRIMARY KEY (`id_penyewa`);

--
-- Indexes for table `tagihan`
--
ALTER TABLE `tagihan`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `id_penyewa` (`id_penyewa`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
