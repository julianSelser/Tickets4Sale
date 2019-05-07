const Layout = ({children}) => {
  return (
    <div>
      <Header />
      <main>{children}</main>
      <Footer />
    </div>
  )
}


const PropTypes = window.PropTypes;

Layout.propTypes = {
  children: PropTypes.element.isRequired,
}
