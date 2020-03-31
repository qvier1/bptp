<?php 	
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$nama = $_POST['nama'];
    $desa = $_POST['desa'];
    $kab_kota = $_POST['kab_kota'];
    $kecamatan = $_POST['kecamatan'];
    $koordinat_lat1 = $_POST['koordinat_lat'];
    $koordinat_lang1 = $_POST['koordinat_lang'];
    $jenis_lahan = $_POST['jenis_lahan'];
    $bahan_induk = $_POST['bahan_induk'];
    $penggunaan_lahan = $_POST['penggunaan_lahan'];
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
    $catatan = $_POST['catatan'];
    
    $koordinat_lat = floatval($koordinat_lat1);
    $koordinat_lang = floatval($koordinat_lang1);

    require_once 'conn.php';
	$query = "INSERT INTO `inputs`(`nama`, `desa`, `kab_kota`, `kecamatan`, `koordinat_lat`, `koordinat_lang`, `jenis_lahan`, `bahan_induk`, `penggunaan_lahan`, `relief`, `kondisi_lahan`, `drainase`, 						`reaksi_tanah`, `tanaman_utama`, `tekstur`, `kedalaman_olah`, `pola_tanam`, `varietas`, `provitas`, `pengunaan_pupuk`,`catatan`) 
							    VALUES 
							    ('$nama','$desa', '$kab_kota','$kecamatan', '$koordinat_lat', '$koordinat_lang', '$jenis_lahan','$bahan_induk',
							    '$penggunaan_lahan', '$relief', '$kondisi_lahan',
							    '$drainase', '$reaksi_tanah', '$tanaman_utama', '$tekstur',
							    '$kedalaman_olah', '$pola_tanam', '$varietas',
							    '$provitas', '$penggunaan_pupuk','$catatan')";

	if (mysqli_query($conn,$query)) {
		$result["success"] = "1";
		header('Content-Type: application/json');
		echo json_encode($result);
		mysqli_close($conn);
	} else {
		$result["success"] = "0";

		header('Content-Type: application/json');
		echo json_encode($result);
		mysqli_close($conn);	
	}
}
// require_once 'conn.php';

// $lat = '-6.479339';
// $lang ='106.8230993';

// $latF = floatval($lat);
// $langF = floatval($lang);

// $query = "INSERT INTO `inputs`(`nama`, `desa`, `kab_kota`, `kecamatan`, `koordinat_lat`, `koordinat_lang`, `jenis_lahan`, `bahan_induk`, `penggunaan_lahan`, `relief`, `kondisi_lahan`, `drainase`, `reaksi_tanah`, `tanaman_utama`, `tekstur`, `kedalaman_olah`, `pola_tanam`, `varietas`, `provitas`, `pengunaan_pupuk`) 
							   
// 							    VALUES 
// 							    ('aaaaaa','aaaaaa' ,'aaaaaa', 'aaaaaa',-6.479339, 106.8230993, 'aaaaaa','aaaaaa',
// 							    'aaaaaa', 'aaaaaa', 'aaaaaa',
// 							    'aaaaaa', 'aaaaaa', 'aaaaaa', 'aaaaaa',
// 							    'aaaaaa', 'aaaaaa', 'aaaaaa',
// 							    'aaaaaa', 'aaaaaa')";
// 	if (mysqli_query($conn,$query)) {
//     	echo "Successs";
//     }else{
//     	echo "Wrong";
//     }

