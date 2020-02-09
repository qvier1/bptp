<?php 

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$username = $_POST['username'];
	$password = $_POST['password'];
	$email = $_POST['email'];
	$name = $_POST['name'];

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
