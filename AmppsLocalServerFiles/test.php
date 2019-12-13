<?php

	$code = $_POST["result"];
    include('simple_html_dom.php');
    $domresults = new simple_html_dom();

    $curl=curl_init();
    curl_setopt($curl, CURLOPT_URL, 'https://www.google.com.tr/search?q='.$code.'&sxsrf=ACYBGNTYmpUPhujIVVIhE8hPtJoEbU_TFQ:1575990318953&source=lnms&tbm=shop&sa=X&ved=2ahUKEwiitoW5ravmAhXKQxUIHbItDH8Q_AUoA3oECAsQBQ&biw=1536&bih=754');
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
    $data = curl_exec($curl);
    $domresults->load($data);

    //Finding the link
	foreach ($domresults->find('h3[class="r"]') as $link) {
        $isim=iconv('ISO-8859-9','UTF-8',$link->find('a',0)->href);
    }
	curl_setopt($curl, CURLOPT_URL, 'www.google.com.tr' .$isim);
    $data = curl_exec($curl);
    curl_close($curl);
    $domresults->load($data);
    $price = array();
	$shop = array();	
	$foundProduct = false;
	//Name
    foreach ($domresults->find('h1[id="product-name"]') as $link) {
    	$name = iconv('ISO-8859-9','UTF-8',$link->plaintext);
        $foundProduct = true;
    }
    //Price
    foreach ($domresults->find('span[class="tiOgyd"]') as $link) {
       array_push($price, iconv('ISO-8859-9','UTF-8',$link->plaintext));
    }
    //Image
    foreach ($domresults->find('div[class="JRlvE XNeeld"]') as $link) {
        $img=iconv('ISO-8859-9','UTF-8',$link->find('img',0)->src);
    }
    //Shop Names
    foreach ($domresults->find('span[class="os-seller-name-primary"]') as $link) {
        array_push($shop,iconv('ISO-8859-9','UTF-8',$link->find('a',0)->plaintext));
    }

    if($foundProduct){
		$jsonCapsule = array(
			'error' => 0,
			'name' =>  $name,
			'image' => $img,
			'shoppers' => $shop,
			'prices' => $price);
	}
	else{
		$jsonCapsule = array(
		'error' => 1);
	}

	echo json_encode($jsonCapsule); 


 ?>
