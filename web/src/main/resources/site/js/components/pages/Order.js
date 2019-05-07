const Order = (props) => {
  const handleSubmit = (e) => {
    e.preventDefault()
    return false
  }

  let show = toJson(props.location.search)

  return (
    <order>
      <h1>{show.title}</h1>
      <h1>${show.price}</h1>
      {
        (show.status == "Open for sale")
            ? (<button onClick={ handleSubmit } id='orderBtn'>Order</button>)
            : (<div className='error'>Show is not open for sale</div>)
      }
    </order>
  )
}