const { Component } = window.React

class Order extends Component {
    constructor(props) {
      super()
      this.show = toJson(props.location.search)
      this.state = {
        message: ''
      }
    }

    handleSubmit(e) {
        e.preventDefault()

        let {id, price, date} = this.show

        let order = {
            showId: Number(id),
            price: Number(price),
            showDate: date
        }

        fetch("orders", {
            method: "POST",
            body: JSON.stringify(order),
            headers:{
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.text())
        .then(message => this.setState({ message }))
    }

    render() {
      return (
        <order>
          <h1>{this.show.title}</h1>
          <h1>${this.show.price}</h1>
          {
            (this.show.status == "Open for sale")
                ? (<button onClick={ (e) => this.handleSubmit(e) } id='orderBtn'>Order</button>)
                : (<div className='error'>Show is not open for sale</div>)
          }
          <br />
          <div>{this.state.message}</div>
        </order>
      )
    }
}