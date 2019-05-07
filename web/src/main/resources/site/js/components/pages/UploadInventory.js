class UploadInventory extends React.Component {
    constructor(props) {
      super(props);
      this.handleSubmit = this.handleSubmit.bind(this)
      this.fileInput = React.createRef()
      this.state = {
        uploadResult: ''
      }
    }

    handleSubmit(event) {
        const self = this;
        event.preventDefault();

        const file = this.fileInput.current.files[0]
        const formData = new FormData();

        formData.append('csv', file);

        file && fetch('inventory', { method: 'POST', body: formData }).then(response => {
            self.setState({ uploadResult: response.ok? "Upload successful!" : "Oops! Something went wrong" })
        })
    }

    render() {
      return (
        <div>
            <h2>Upload inventory</h2>
            <form onSubmit={this.handleSubmit}>
                <input type="file" ref={this.fileInput} />
                <br />
                <button type="submit">Submit</button>
            </form>
            <br />
            <div>{this.state.uploadResult}</div>
        </div>
      )
    }
}