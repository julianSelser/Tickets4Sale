const { Component } = window.React

class Shows extends Component {
    constructor(){
        super()
        this.state = {
            shows: {
                     "inventory": [{
                       "genre": "drama",
                       "shows": [{
                         "id": 1,
                         "title": "Everyman",
                         "ticketsLeft": 100,
                         "ticketsAvailable": 10,
                         "status": "Open for sale",
                         "price": 40
                       }]
                     }, {
                       "genre": "comedy",
                       "shows": [{
                         "id": 2,
                         "title": "Comedy of Errors",
                         "ticketsLeft": 100,
                         "ticketsAvailable": 10,
                         "status": "Open for sale",
                         "price": 50
                       }]
                     }, {
                       "genre": "musical",
                       "shows": [{
                         "id": 3,
                         "title": "Cats",
                         "ticketsLeft": 50,
                         "ticketsAvailable": 5,
                         "status": "Open for sale",
                         "price": 70
                       }]
                     }]
                   }
        }
    }
//    componentDidMount(){
//        console.log("mounted")
//    }
    render() {
      let { inventory } = this.state.shows

      return <div>
        { inventory.map(item => <ShowsTable genreShows={item}></ShowsTable>) }
      </div>
  }
}
