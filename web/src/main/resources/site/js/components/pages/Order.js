const Order = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    return false;
  };

  return (
    <div>
      <h1>Contact Us</h1>
      <form onClick={handleSubmit}>
        <input type='text' placeholder='name' />
        <input type='text' placeholder='email' />
        <textarea />
        <button>Submit</button>
      </form>
    </div>
  );
};