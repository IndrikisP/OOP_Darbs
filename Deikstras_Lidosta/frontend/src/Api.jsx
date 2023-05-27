import Axios from "axios";

export const getAirportList = async (setAirportList) => {
    try {
        const resp = await Axios.get("http://localhost:8080/get/allAirports");
        const data = resp.data;
        console.log(data);
        const sortedData = data.sort((a, b) => {
            const airportA = a.cityName.toLowerCase();
            const airportB = b.cityName.toLowerCase();
            return airportA.localeCompare(airportB);
        });
        setAirportList(sortedData);
    } catch (error) {
        console.error("Error fetching airport data:", error);
    }
};

export const getAllFilters = (setAllFilters) => {
    const data = [
        'price',
        'distance',
        'stop'
    ];
    setAllFilters(data);
};

export const getFlightInfo = async (setAirportList) => {
    try {

        const data = [
            [
                {
                    FlightId: "123",
                    FromId: "Lisbon Portela (LIS)",
                    ToId: "Brussels (BRU)"
                },
                {
                    FlightId: "234",
                    FromId: "Brussels (BRU)",
                    ToId: "Vienna (VIE)"
                },
                {
                    FlightId: "345",
                    FromId: "Vienna (VIE)",
                    ToId: "Copenhagen (CPH)"
                },
                {
                    FlightId: "456",
                    FromId: "Copenhagen (CPH)",
                    ToId: "Riga (RIX)"
                }
            ],
            [
                {
                    FlightId: "789",
                    FromId: "Belgrade Nikola Tesla (BEG)",
                    ToId: "Antalya (AYT)"
                },
                {
                    FlightId: "420",
                    FromId: "Antalya (AYT)",
                    ToId: "Tel Aviv Ben Gurion (TLV)"
                }
            ],
            [
                {
                    FlightId: "789",
                    FromId: "London Heathrow (LHR)",
                    ToId: "Boston Logan (BOS)"
                }
            ]
        ];

        setAirportList(data);
    } catch (error) {
        console.error(error);
    }
};

export const getAllFlights = async (selectedItemOrigin,selectedItemDestination,selectedParam,setFlightData) => {
    try {
        const resp = await Axios.get('http://localhost:8080/get/paths?fromid='+selectedItemOrigin+'&toid='+selectedItemDestination+'&paramname='+selectedParam+'&parameter=1200');
        const data = resp.data;
        console.log(data);
        setFlightData(data);
    } catch (error) {
        console.error("Error fetching flights data:", error);
    }
}