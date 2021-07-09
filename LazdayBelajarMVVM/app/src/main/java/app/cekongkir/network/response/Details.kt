package app.cekongkir.network.response

data class Details(
    val destination: String,
    val origin: String,
    val receiver_address1: String,
    val receiver_address2: String,
    val receiver_address3: String,
    val receiver_city: String,
    val receiver_name: String,
    val shipper_address1: String,
    val shipper_address2: String,
    val shipper_address3: String,
    val shipper_city: String,
    val shippper_name: String,
    val waybill_date: String,
    val waybill_number: String,
    val waybill_time: String,
    val weight: String
)