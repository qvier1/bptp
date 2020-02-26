<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $username = $_POST['username'];
    $password = $_POST['password'];

    require_once 'conn.php';

    $sql = "SELECT * FROM user WHERE username='$username' AND password='$password'; ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {
        $row = mysqli_fetch_assoc($response);
        $index['username'] = $row['username'];
        $index['email'] = $row['email'];
        $index['name'] = $row['name'];
        array_push($result['login'], $index);
        $result['success'] = "1";
	} else {
		$result['success'] = "0";
	}
	echo json_encode($result);
    mysqli_close($conn);
}