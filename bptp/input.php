<?php 
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$nama = $_POST['name'];
    $desa = $_POST['desa'];
    $kab_kota = $_POST['kab_kota'];
    $jenis_lahan = $_POST['jenis_lahan'];
    $bahan_induk = $_POST['bahan_induk'];
    $pengguna_lahan = $_POST['pengguna_lahan'];
    $relief = $_POST['relief'];
    $kondisi_lahan = $_POST['kondisi_lahan'];
    $drainase = $_POST['drainase'];
    $reaksi_tanah =$_POST['reaksi_tanah'];
    $tanaman_utama = $_POST['tanaman_utama'];
    $tekstur = $_POST['tekstur'];
    $kedalaman_olah = $_POST['kedalaman_olah'];
    $pola_tanam =$_POST['pola_tanam'];
    $varietas = $_POST['varietas'];
    $provitas = $_POST['provitas'];
    $penggunaan_pupuk = $_POST['penggunaan_pupuk'];
    $kecamatan = $_POST['kecamatan'];

    require_once 'conn.php';
	$query = "INSERT INTO inputs (nama, kab_kota, kecamatan, desa,koordinat, jenis_lahan,bahan_induk,
							    penggunaan_lahan, relief, kondisi_lahan,
							    drainase, reaksi_tanah, tanaman_utama, tekstur,
							    kedalaman_olah,pola_tanaman,varietas,
							    provitas, penggunaan_pupuk) 
							    VALUES 
							    ('$nama','$kab_kota','$kecamatan','$desa', '$koordinat', '$jenis_lahan','$bahan_induk',
							    '$penggunaan_lahan', '$relief', '$kondisi_lahan',
							    '$drainase', '$reaksi_tanah', '$tanaman_utama', '$tekstur',
							    '$kedalaman_olah', '$pola_tanaman', '$varietas',
							    '$provitas', '$penggunaan_pupuk')";

	if (mysqli_query($conn,$query)) {
		$result["success"] = "1";
		$result["message"] = "success";

		echo json_encode($result);
		mysqli_close($conn);
	} else {
		$result["success"] = "0";
		$result["message"] = "error";
		echo json_encode($result);
		mysqli_close($conn);	
	}
}























































	require_once 'conn.php';
	$query = "INSERT INTO user (username, password, name, email) VALUES ('$username', '$password', '$name', '$email')";

	if (mysqli_query($conn,$query)) {
		$result["success"] = "1";
		$result["message"] = "success";

		echo json_encode($result);
		mysqli_close($conn);
	} else {
		$result["success"] = "0";
		$result["message"] = "error";
		echo json_encode($result);
		mysqli_close($conn);	
	}
}