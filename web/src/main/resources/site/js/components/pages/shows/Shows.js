const { Component } = window.React

class Shows extends Component {
    constructor(){
        super()
        this.showDateInput = React.createRef()
        this.state = {
            shows: {inventory: []},
        }

        this.fetchInventory()
    }

    fetchInventory() {
        let self = this
        let current = this.showDateInput.current
        let showDate = current && current.value || toIsoDate(new Date())

        fetch("inventory", {
                method: "POST",
                body: JSON.stringify({ showDate: showDate }),
                headers:{
                    'Content-Type': 'application/json'
                }
        })
        .then(response => response.json())
        .then(inventory => self.setState({ shows: inventory }))
    }

    render() {
      let { inventory } = this.state.shows

      return (
          <div>
            <br />
            <label>Show date:</label>
            <input className='inline' type='date' ref={this.showDateInput} defaultValue={ toIsoDate(new Date()) }/>
            <button onClick={ () => this.fetchInventory() } id='showDateSubmit' className='inline'>Submit</button>
            { inventory.map(item => <ShowsTable genreShows={item}></ShowsTable>) }
          </div>
      )
  }
}
