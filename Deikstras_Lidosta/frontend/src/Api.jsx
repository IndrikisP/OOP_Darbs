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
            FlightId: "a",
            FromId: "b",
            ToId: "c"
          },
          {
            FlightId: "d",
            FromId: "e",
            ToId: "f"
          }
        ],
        [
          {
            FlightId: "g",
            FromId: "h",
            ToId: "i"
          },
          {
            FlightId: "j",
            FromId: "k",
            ToId: "l"
          }
        ]
      ];
  
      setAirportList(data);
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };