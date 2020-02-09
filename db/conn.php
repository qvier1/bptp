<?php 
$dbhost = 'remotemysql.com:3036';
$dbuser = 'wwGZDKTJbI';
$dbpass = 'LUYEARtftk';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass);

if(! $conn ) {
die('Could not connect: ' . mysqli_error());
}
echo 'Connected successfully<br />';
mysqli_close($conn);
      

 ?>