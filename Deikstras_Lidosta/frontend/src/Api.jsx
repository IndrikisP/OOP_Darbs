export const getAirportList = async (setAirportList) => {
    try {
      const data = [
        {
            id: 'ff846caa-fc5a-11ed-be56-0242ac120002',
            airport: 'Lisbon Portela (LIS)'
        },
        {
            id: 'ff846f16-fc5a-11ed-be56-0242ac120002',
            airport: 'Brussels (BRU)'
        },
        {
            id: 'ff847038-fc5a-11ed-be56-0242ac120002',
            airport: 'Riga (RIX)'
        },
        {
            id: 'ff847222-fc5a-11ed-be56-0242ac120002',
            airport: 'Vienna (VIE)'
        },
        {
            id: 'ff84731c-fc5a-11ed-be56-0242ac120002',
            airport: 'Copenhagen (CPH)'
        },
        {
            id: '46376844-fc5a-11ed-be56-0242ac120002',
            airport: 'Belgrade Nikola Tesla (BEG)'
        },
        {
            id: '46376f6a-fc5a-11ed-be56-0242ac120002',
            airport: 'Antalya (AYT)'
        },
        {
            id: '46376f6a-fc5a-11ed-be56-0242ac120002',
            airport: 'Tel Aviv Ben Gurion (TLV)'
        },
      ];
  
      const sortedData = data.sort((a, b) => {
        const airportA = a.airport.toLowerCase();
        const airportB = b.airport.toLowerCase();
        return airportA.localeCompare(airportB);
      });

      console.log(sortedData);
  
      setAirportList(sortedData);
    } catch (error) {
      console.error(error);
    }
  };

  export const getAllFilters = (setAllFilters) => {
    const data = [
      'Cheapest',
      'Fastest',
      'Non-stop'
    ];
    setAllFilters(data);
    console.log(data);
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
        ]
      ];
  
      setAirportList(data);
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };