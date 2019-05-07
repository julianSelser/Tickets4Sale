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

    getShowDate() {
        let current = this.showDateInput.current
        let date = current && current.value || window.saved || toIsoDate(new Date())

        window.saved = date

        return date
    }

    fetchInventory() {
        let self = this
        let showDate = this.getShowDate()

        fetch("inventory", {
                method: "POST",
                body: JSON.stringify({ showDate }),
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
            <input className='inline' type='date' ref={this.showDateInput} defaultValue={ this.getShowDate() }/>
            <button onClick={ () => this.fetchInventory() } id='showDateSubmit' className='inline'>Submit</button>
            { inventory.map(
                item => <ShowsTable chosenDate={this.getShowDate()} genreShows={item}></ShowsTable>)
            }
          </div>
      )
  }
}
