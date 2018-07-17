import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import Chip from '@material-ui/core/Chip';
import Paper from '@material-ui/core/Paper';
import TagFacesIcon from '@material-ui/icons/TagFaces';

const styles = theme => ({
    root: {
        display: 'flex',
        justifyContent: 'left',
        flexWrap: 'wrap',
        padding: theme.spacing.unit / 2,
    },
    chip: {
        margin: theme.spacing.unit / 2,
    },

});

class Tag extends React.Component{
    constructor(prop){
        super(prop);
        this.state={
            ifPick:false,
            data:this.props.data
        }
        this.handleClick=this.handleClick.bind(this);
    }

    handleClick = data => () => {
        if(!this.state.ifPick)this.props.Choose(data.tagId);
        else this.props.DelChoose(data.tagId);
        this.setState({
            ifPick:!this.state.ifPick
        });
    };
    componentWillMount(){

    }


    render() {
        const { anchorEl } = this.state;
        return(
            <Chip
                key = {this.state.data.tagId}
                avatar={this.state.ifPick?
                    <Avatar><TagFacesIcon  /></Avatar>:null}
                label={this.state.data.tagName}
                onClick={this.handleClick(this.state.data)}
            />
        )
    }


}

class TagList extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick=this.handleClick.bind(this)
        this.DeleteClick=this.DeleteClick.bind(this)
    }
    state = {
        TagList:[],
        ChooseTag:[]
    };


    componentWillMount(){
        fetch('http://localhost:8080/Food/AllTags',{
            credentials: 'include',
            method:'GET',
            mode:'cors',
        }).then(response=>{
            console.log('Request successful',response);
            return response.json().then(result=>{
                this.setState({
                   TagList:result
                });
            });
        });
    }

    componentWillReceiveProps = (nextProps) =>{

    };

    handleClick (tagId){
        let temp = this.state.ChooseTag;
        temp.push(tagId);
        this.setState({
            ChooseTag :temp
        })
        //alert("add"+temp.length);
    };

    DeleteClick (tagId){
        let temp = this.state.ChooseTag;
        let index=0;
        for(let i=0;i<temp.length;i++){
            if(tagId===temp[i]){index=i;}
        }
        temp.splice(index);
        this.setState({
            ChooseTag :temp
        })
        //alert("del"+temp.length);

    };

    render() {
        const { classes } = this.props;
        const { anchorEl } = this.state;
        return(
            <Paper className={classes.root}>
                {this.state.TagList.map(data => {
                    return (
                        <Tag data={data} key={data.tagId} Choose={this.handleClick} DelChoose={this.DeleteClick}/>
                    );
                })}
                </Paper>
        )
    }
}
TagList.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(TagList);

