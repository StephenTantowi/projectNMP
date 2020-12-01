<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "nmp160418024", "ubaya", "nmp160418024");

	if($c->connect_errno) 
	{
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	//Cek data yang dikirim
	if($_POST['iduser'] && $_POST['idproduct'] && $_POST['jumlah']) 
	{
		$iduser = $_POST['iduser'];
		$idproduct = $_POST['idproduct'];
		$jumlah =  $_POST['jumlah'];

		//Cek isi data
		$sql = "SELECT * from keranjangsementara where idproduct = '$idproduct' && iduser = '$iduser'";
		$hasil = $c->query($sql);
		$hasilobj = mysqli_fetch_object($hasil);
		$cek = $hasilobj->totalHarga;

		//Cek data kosong atau tidak

		//Kalau kosong
		if(is_null($cek))
		{
			//Ambil harga dari tabel product
			$sql2 = "SELECT harga from product where idproduct = '$idproduct'";
			$hasil2 = $c->query($sql2);
			$hasilobj2 = mysqli_fetch_object($hasil2);
			$harga = $hasilobj2->harga;

			$totalHarga = $jumlah * $harga;

			//Tambah keranjang sementara
			$sql3 = "INSERT INTO keranjangsementara(iduser, idproduct, jumlah, totalHarga) values ('$iduser', '$idproduct', '$jumlah', '$totalHarga')";
			$c->query($sql3);
			$arr3 = array("result" => "OK", 
			"sql"	=> $sql3,
			"message" => "keranjang sementara added");
			echo json_encode($arr3);
		}
		//Kalau tidak
		else
		{
			//Ambil harga dari tabel product
			$sql2 = "SELECT harga from product where idproduct = '$idproduct'";
			$hasil2 = $c->query($sql2);
			$hasilobj2 = mysqli_fetch_object($hasil2);
			$harga = $hasilobj2->harga;
			
			//Update keranjangsementara
			$sql1 = "UPDATE keranjangsementara SET jumlah = jumlah + 1, totalHarga = totalHarga + $harga WHERE idproduct = '$idproduct' && iduser = '$iduser'";

			$c->query($sql1);
			$arr1 = array("result" => "OK", 
				"sql"	=> $sql1,
				"message" => "keranjangsementara updated");
			echo json_encode($arr1);
		}		
	} 
	else 
	{
		$arr = array("result" => "ERROR", 
			"message" => "error");
		echo json_encode($arr);
	}
?>